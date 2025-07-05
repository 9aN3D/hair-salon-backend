package pl.edu.wit.hairsalon.reservation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

class ReservationCalculator {

    private final MemberFacade memberFacade;
    private final HairdresserFacade hairdresserFacade;
    private final ServiceFacade serviceFacade;
    private final ScheduledEventFacade scheduledEventFacade;

    ReservationCalculator(MemberFacade memberFacade,
                          HairdresserFacade hairdresserFacade,
                          ServiceFacade serviceFacade,
                          ScheduledEventFacade scheduledEventFacade) {
        this.memberFacade = memberFacade;
        this.hairdresserFacade = hairdresserFacade;
        this.serviceFacade = serviceFacade;
        this.scheduledEventFacade = scheduledEventFacade;
    }

    ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command) {
        requireNonNull(command.step(), "ReservationCalculator, command step must not be null");
        var member = getMember(memberId);
        var reservationCalculation = switch (command.step()) {
            case SELECT_SERVICES -> buildSelectServicesStep(command);
            case SELECT_HAIRDRESSER -> buildSelectHairdresserStep(command);
            case SELECT_START_TIME -> buildSelectStartTimeStep(command);
            case SUMMARY -> buildSelectSummaryStep(command);
        };
        return reservationCalculation.validate()
                .toDtoBuilder()
                .memberId(member.id())
                .build();
    }

    private SelectServicesStepReservationCalculation buildSelectServicesStep(ReservationCalculateCommand command) {
        return new SelectServicesStepReservationCalculation(new BaseReservationCalculation(command.date()), getAvailableServices(command))
                .fillSelectedServices(command.selectedServiceIds());
    }

    private SelectHairdresserStepReservationCalculation buildSelectHairdresserStep(ReservationCalculateCommand command) {
        return new SelectHairdresserStepReservationCalculation(buildSelectServicesStep(command), getAvailableHairdressers(command))
                .fillSelectedHairdresser(command.hairdresserId());
    }

    private SelectStartTimeStepReservationCalculation buildSelectStartTimeStep(ReservationCalculateCommand command) {
        return new SelectStartTimeStepReservationCalculation(buildSelectHairdresserStep(command))
                .fillSelectedStartTime(command.startTime())
                .verifyReservedTimes(scheduledEventFacade::count);
    }

    private SelectSummaryStepReservationCalculation buildSelectSummaryStep(ReservationCalculateCommand command) {
        return new SelectSummaryStepReservationCalculation(buildSelectStartTimeStep(command));
    }

    private MemberDto getMember(String memberId) {
        return memberFacade.findOne(memberId);
    }

    private List<ReservationHairdresserService> getAvailableServices(ReservationCalculateCommand command) {
        var availableHairdresserServiceIds = hairdresserFacade.findAll(HairdresserFindQuery.empty())
                .stream()
                .filter(hairdresser -> !hairdresserFacade.getAvailableTimeDurations(hairdresser.id(), command.date()).isEmpty())
                .flatMap(hairdresser -> hairdresser.serviceIds().stream())
                .collect(toSet());
        if (availableHairdresserServiceIds.isEmpty()) {
            return List.of();
        }
        var serviceIdToReservationHairdresserService = prepareMapServices(availableHairdresserServiceIds);
        return availableHairdresserServiceIds.stream()
                .map(serviceIdToReservationHairdresserService::get)
                .map(ReservationHairdresserService::new)
                .toList();
    }

    private List<ReservationHairdresser> getAvailableHairdressers(ReservationCalculateCommand command) {
        if (isNull(command.selectedServiceIds()) || command.selectedServiceIds().isEmpty()) {
            return List.of();
        }
        var allHairdresserServiceIds = new HashSet<String>();
        var availableHairdressers = hairdresserFacade.findAll(HairdresserFindQuery.ofHairdresserServiceIds(command.selectedServiceIds()))
                .stream()
                .peek(hairdresser -> allHairdresserServiceIds.addAll(hairdresser.serviceIds()))
                .toList();
        if (availableHairdressers.isEmpty()) {
            return List.of();
        }
        var serviceIdToReservationHairdresserService = prepareMapServices(allHairdresserServiceIds);
        var selectedServices = collectServicesByIds(command.selectedServiceIds(), serviceIdToReservationHairdresserService);
        return availableHairdressers.stream()
                .map(hairdresser -> new ReservationHairdresser(hairdresser, getAvailableStartTimes(hairdresser.id(), command.date(), selectedServices))
                        .addAllServices(collectServicesByIds(hairdresser.serviceIds(), serviceIdToReservationHairdresserService)))
                .filter(hairdresser -> hairdresser.hasAvailableStartTimesForDate(command.date()))
                .toList();
    }

    private Map<String, ServiceDto> prepareMapServices(Set<String> serviceIds) {
        if (serviceIds.isEmpty()) {
            return Map.of();
        }
        var findQuery = ServiceFindQuery.withIds(serviceIds);
        var pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        return serviceFacade.findAll(findQuery, pageRequest)
                .stream()
                .collect(toMap(ServiceDto::id, Function.identity()));
    }

    private List<ServiceDto> collectServicesByIds(Set<String> serviceIds, Map<String, ServiceDto> serviceIdToReservationHairdresserService) {
        return serviceIds.stream()
                .map(serviceIdToReservationHairdresserService::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date, List<ServiceDto> selectedServices) {
        return hairdresserFacade.getAvailableStartTimes(hairdresserId, date, calculateTotalDuration(selectedServices));
    }

    private Duration calculateTotalDuration(List<ServiceDto> services) {
        return services.stream()
                .map(ServiceDto::getDurationInMinutes)
                .reduce(Duration.ZERO, Duration::plus);
    }

}
