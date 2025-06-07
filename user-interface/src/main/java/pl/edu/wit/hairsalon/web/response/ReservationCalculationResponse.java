package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "hairdresser")
public class ReservationCalculationResponse {

    @NotNull
    HairdresserResponse hairdresser;

    @NotNull
    DateRangeDto times;

    List<ServiceResponse> selectedServices;

    BigDecimal totalPrice;

}
