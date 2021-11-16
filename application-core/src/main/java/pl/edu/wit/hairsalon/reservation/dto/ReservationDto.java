package pl.edu.wit.hairsalon.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ReservationDto {

    String id;

    String memberId;

    ReservationHairdresserDto hairdresser;

    DateRangeDto times;

    List<ServiceDto> selectedServices;

    BigDecimal totalPrice;

    LocalDateTime creationDateTime;

}
