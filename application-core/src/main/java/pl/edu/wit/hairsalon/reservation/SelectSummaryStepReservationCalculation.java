package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

record SelectSummaryStepReservationCalculation(SelectStartTimeStepReservationCalculation previousCalculation) implements ReservationCalculation {

    @Override
    public LocalDate date() {
        return previousCalculation.date();
    }

    @Override
    public LocalDateTime now() {
        return previousCalculation.now();
    }

    @Override
    public ReservationCalculationDto.Builder toDtoBuilder() {
        return previousCalculation.toDtoBuilder();
    }

    @Override
    public SelectSummaryStepReservationCalculation validate() {
        previousCalculation.fullValidate();
        return this;
    }

}
