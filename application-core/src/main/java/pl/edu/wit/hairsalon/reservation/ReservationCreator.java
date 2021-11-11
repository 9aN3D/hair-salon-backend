package pl.edu.wit.hairsalon.reservation;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

@RequiredArgsConstructor
class ReservationCreator {

    ReservationDto create(String memberId, ReservationMakeCommand command) {
        return null;
    }

}
