package pl.edu.wit.hairsalon.sharedKernel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor
public class MoneyDto {

    BigDecimal amount;

    CurrencyDto currency;

}
