package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationDto;

import java.util.Map;

public class InMemoryReservationPort implements ReservationPort {

    public static String RESERVATION_ID = "1R";

    Map<String, ReservationDto> map = Map.of(
            RESERVATION_ID, new ReservationDto(RESERVATION_ID)
    );

    @Override
    public ReservationDto findOneOrThrow(String reservationId) {
        return map.get(reservationId);
    }

}
