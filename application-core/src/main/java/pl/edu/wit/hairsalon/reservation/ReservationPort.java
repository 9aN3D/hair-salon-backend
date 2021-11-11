package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

public interface ReservationPort {

    ReservationDto save(ReservationDto reservation);

    ReservationDto findOneOrThrow(String reservationId);

}
