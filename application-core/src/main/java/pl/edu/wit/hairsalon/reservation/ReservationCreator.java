package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.eventBus.DomainEventPublisher;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

class ReservationCreator {

    private final IdGenerator idGenerator;
    private final ReservationPort reservationPort;
    private final ReservationCalculator reservationCalculator;
    private final DomainEventPublisher domainEventPublisher;

    public ReservationCreator(IdGenerator idGenerator, ReservationPort reservationPort, ReservationCalculator reservationCalculator, DomainEventPublisher domainEventPublisher) {
        this.idGenerator = idGenerator;
        this.reservationPort = reservationPort;
        this.reservationCalculator = reservationCalculator;
        this.domainEventPublisher = domainEventPublisher;
    }

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
                .hairdresser(reservationCalculation.selectedHairdresser()
                        .map(ReservationHairdresser::new)
                        .orElseThrow(() -> new IllegalArgumentException("Hairdresser must not be null")))
                .times(new DateRange(reservationCalculation.dateTimes()))
                .selectedServices(toReservationHairdresserService(reservationCalculation.selectedServices()))
                .totalPrice(reservationCalculation.totalPrice())
                .creationDateTime(reservationCalculation.calculationTime())
                .build();
    }

    private List<ReservationHairdresserService> toReservationHairdresserService(List<ServiceDto> selectedServices) {
        return selectedServices.stream()
                .map(ReservationHairdresserService::new)
                .map(ReservationHairdresserService::validate)
                .collect(toList());
    }

}
