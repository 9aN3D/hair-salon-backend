package pl.edu.wit.hairsalon.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppReservationFacade implements ReservationFacade {

    private final ReservationPort reservationPort;
    private final ReservationCreator creator;
    private final ReservationCalculator calculator;

    @Override
    public void make(String memberId, ReservationMakeCommand command) {
        log.trace("Reserving hairdresser services {memberId: {}, command: {}}", memberId, command);
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Hairdresser service reserve command must not be null");
        var reservation = creator.create(memberId, command);
        log.info("Reserved hairdresser services {dto: {}}", reservation);
    }

    @Override
    public ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command) {
        log.trace("Calculating hairdresser service reservation {memberId: {}, command: {}}", memberId, command);
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Hairdresser service reservation calculate command must not be null");
        var calculatedReservation = calculator.calculate(memberId, command);
        log.info("Calculated hairdresser service reservation {dto: {}}", calculatedReservation);
        return calculatedReservation;
    }

    @Override
    public ReservationDto findOne(String reservationId) {
        log.trace("Getting hairdresser service reservation {reservationId: {}}", reservationId);
        requireNonNull(reservationId, "Reservation id must not be null");
        var reservation = reservationPort.findOneOrThrow(reservationId);
        log.info("Got hairdresser service reservation {dto: {}}", reservation);
        return reservation;
    }

}
