package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

import static java.util.Objects.requireNonNull;

class AppReservationFacade implements ReservationFacade {

    private final ReservationPort reservationPort;
    private final ReservationCreator creator;
    private final ReservationCalculator calculator;

    AppReservationFacade(ReservationPort reservationPort, ReservationCreator creator, ReservationCalculator calculator) {
        this.reservationPort = reservationPort;
        this.creator = creator;
        this.calculator = calculator;
    }

    @Override
    public void make(String memberId, ReservationMakeCommand command) {
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Hairdresser service reserve command must not be null");
        creator.create(memberId, command);
    }

    @Override
    public ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command) {
        requireNonNull(memberId, "Member id must not be null");
        requireNonNull(command, "Hairdresser service reservation calculate command must not be null");
        return calculator.calculate(memberId, command);
    }

    @Override
    public ReservationDto findOne(String reservationId) {
        requireNonNull(reservationId, "Reservation id must not be null");
        return reservationPort.findOneOrThrow(reservationId);
    }

}
