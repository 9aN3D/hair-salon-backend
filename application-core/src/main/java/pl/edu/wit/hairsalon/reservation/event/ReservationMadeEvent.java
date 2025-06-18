package pl.edu.wit.hairsalon.reservation.event;

import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

public record ReservationMadeEvent(
        ReservationDto reservation
) {

}
