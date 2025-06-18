package pl.edu.wit.hairsalon.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

class LoggableReservationFacade implements ReservationFacade {

    private final Logger log;
    private final ReservationFacade delegate;

    LoggableReservationFacade(ReservationFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableReservationFacade.class);
        this.delegate = delegate;
    }

    @Override
    public void make(String memberId, ReservationMakeCommand command) {
        log.trace("Reserving hairdresser services {memberId: {}, command: {}}", memberId, command);
        delegate.make(memberId, command);
        log.info("Reserved hairdresser services {memberId: {}, command: {}}", memberId, command);
    }

    @Override
    public ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command) {
        log.trace("Calculating hairdresser service reservation {memberId: {}, command: {}}", memberId, command);
        var result = delegate.calculate(memberId, command);
        log.info("Calculated hairdresser service reservation {dto: {}}", result);
        return result;
    }

    @Override
    public ReservationDto findOne(String reservationId) {
        log.trace("Getting hairdresser service reservation {reservationId: {}}", reservationId);
        var dto = delegate.findOne(reservationId);
        log.info("Got hairdresser service reservation {dto: {}}", dto);
        return dto;
    }

}
