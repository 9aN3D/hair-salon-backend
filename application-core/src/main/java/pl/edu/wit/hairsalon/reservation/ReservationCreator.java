package pl.edu.wit.hairsalon.reservation;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.eventbus.DomainEventPublisher;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
class ReservationCreator {

    private final IdGenerator idGenerator;
    private final ReservationPort reservationPort;
    private final ReservationCalculator reservationCalculator;
    private final DomainEventPublisher domainEventPublisher;

    ReservationDto create(String memberId, ReservationMakeCommand command) {
        var calculatedReservation = reservationCalculator.calculate(memberId, command.toCalculateCommand());
        var newReservation = createNewReservation(memberId, calculatedReservation)
                .validate();
        var savedReservationDto = reservationPort.save(newReservation.toDto());
        newReservation.publishCreateEvent(domainEventPublisher::publishEvent);
        return savedReservationDto;
    }

    private Reservation createNewReservation(String memberId, ReservationCalculationDto reservationCalculation) {
        return Reservation.builder()
                .id(idGenerator.generate())
                .memberId(memberId)
                .hairdresser(new ReservationHairdresser(reservationCalculation.getHairdresser()))
                .times(new DateRange(reservationCalculation.getTimes()))
                .selectedServices(toReservationHairdresserService(reservationCalculation.getSelectedServices()))
                .totalPrice(reservationCalculation.getTotalPrice())
                .creationDateTime(now())
                .build();
    }

    private List<ReservationHairdresserService> toReservationHairdresserService(List<ServiceDto> selectedServices) {
        return selectedServices.stream()
                .map(ReservationHairdresserService::new)
                .map(ReservationHairdresserService::validate)
                .collect(toList());
    }

}
