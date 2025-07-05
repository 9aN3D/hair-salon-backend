package pl.edu.wit.hairsalon.reservation;

import pl.edu.wit.hairsalon.reservation.dto.ReservationCalculationDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;

interface ReservationCalculation extends SelfValidator<ReservationCalculation> {

    LocalDate date();

    LocalDateTime now();

    ReservationCalculationDto.Builder toDtoBuilder();
    
}
