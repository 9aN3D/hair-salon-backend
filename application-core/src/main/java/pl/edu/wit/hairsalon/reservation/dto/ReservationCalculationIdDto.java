package pl.edu.wit.hairsalon.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationCalculationIdDto(
        ReservationHairdresserDto hairdresser,
        LocalDate date,
        LocalDateTime calculationTime
) {

}
