package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.reservation.ReservationFacade;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static pl.edu.wit.hairsalon.appointment.AppointmentNotificationName.REMINDER;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESERVED;

@RequiredArgsConstructor
class AppointmentCreator {

    private final AppointmentPort appointmentPort;
    private final IdGenerator idGenerator;
    private final ServiceFacade serviceFacade;
    private final MemberFacade memberFacade;
    private final HairdresserFacade hairdresserFacade;
    //private final NotificationFacade notificationFacade; TODO create AppointmentNotification

    AppointmentDto create(AppointmentCreateCommand command) {
        var appointment = createNewAppointment(command)
                .validate()
                .verifyReservedTimes(appointmentPort::count);
        return appointmentPort.save(appointment.toDto());
    }

    private Appointment createNewAppointment(AppointmentCreateCommand command) {
        return Appointment.builder()
                .id(idGenerator.generate())
                .reservationId(command.getReservationId())
                .memberId(getMember(command.getMemberId()).getId())
                .times(new DateRange(command.getTimes()))
                .services(new AppointmentServices().addAll(getServices(command.getServiceIds())))
                .status(RESERVED)
                .statusModificationDateTime(LocalDateTime.now())
                .hairdresserId(getHairdresser(command.getHairdresserId()).getId())
                .notification(new AppointmentNotification(REMINDER))
                .build();
    }

    private MemberDto getMember(String memberId) {
        return memberFacade.findOne(memberId);
    }

    private List<ServiceDto> getServices(Set<String> serviceIds) {
        var findQuery = ServiceFindQuery.withIds(serviceIds);
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(ASC, "price"));
        return serviceFacade.findAll(findQuery, pageRequest).getContent();
    }

    private HairdresserDto getHairdresser(String hairdresserId) {
        return hairdresserFacade.findOne(hairdresserId);
    }

}
