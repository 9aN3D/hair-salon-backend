package pl.edu.wit.hairsalon.reservation.dto;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ReservationCalculationDto {

    ReservationHairdresserDto hairdresser;

    DateRangeDto times;

    List<ServiceDto> selectedServices;

    BigDecimal totalPrice;

}
