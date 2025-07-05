package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

record BaseReservationCalculation(LocalDate date, LocalDateTime now) implements ReservationCalculation {

    BaseReservationCalculation(LocalDate date) {
        this(date, LocalDateTime.now());
    }

    @Override
    public ReservationCalculation validate() {
        requireNonNull(date, "BaseReservationCalculation, date must not be null");
        if (date.isBefore(now.toLocalDate())) {
            throw new ValidationException(
                    format("Reservation calculation start date %s is before %s", date, now.toLocalDate())
            );
        }
        return this;
    }

    @Override
    public ReservationCalculationDto.Builder toDtoBuilder() {
        return ReservationCalculationDto.builder()
                .date(date)
                .calculationTime(now);
    }

}
