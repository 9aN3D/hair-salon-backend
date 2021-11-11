package pl.edu.wit.hairsalon.reservation.event;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

@Value
@Builder
public class ReservationMadeEvent {

    ReservationDto reservation;

    public ReservationMadeEvent(ReservationDto reservation) {
        this.reservation = reservation;
    }

}
