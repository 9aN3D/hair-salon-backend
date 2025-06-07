package pl.edu.wit.hairsalon.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
class ReservationCalculator {

    private final MemberFacade memberFacade;
    private final HairdresserFacade hairdresserFacade;
    private final ServiceFacade serviceFacade;
    private final ScheduledEventFacade scheduledEventFacade;

    ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command) {
        getMember(memberId);
        var hairdresser = hairdresserFacade.findOne(command.getHairdresserId());
        var hairdresserServices = getHairdresserServices(hairdresser.serviceIds());
        return new ReservationCalculation(hairdresser, command.getStartDateTime())
                .addReservationHairdresserServices(hairdresserServices)
                .validate()
                .addSelectedServiceIds(command.getSelectedServiceIds())
                .verifyReservedTimes(scheduledEventFacade::count)
                .toDto();
    }

    private MemberDto getMember(String memberId) {
        return memberFacade.findOne(memberId);
    }

    private List<ServiceDto> getHairdresserServices(Set<String> serviceIds) {
        var findQuery = ServiceFindQuery.withIds(serviceIds);
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        return serviceFacade.findAll(findQuery, pageRequest).getContent();
    }

}
