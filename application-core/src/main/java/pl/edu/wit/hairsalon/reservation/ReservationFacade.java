package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

public interface ReservationFacade {

    void make(String memberId, ReservationMakeCommand command);

    ReservationCalculationDto calculate(String memberId, ReservationCalculateCommand command);

    ReservationDto findOne(String reservationId);

}
