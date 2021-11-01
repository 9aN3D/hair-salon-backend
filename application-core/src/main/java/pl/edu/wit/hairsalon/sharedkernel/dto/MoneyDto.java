package pl.edu.wit.hairsalon.sharedkernel.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class MoneyDto {

    BigDecimal amount;

    CurrencyDto currency;

}
