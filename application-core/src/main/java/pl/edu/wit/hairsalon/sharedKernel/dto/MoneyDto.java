package pl.edu.wit.hairsalon.sharedKernel.dto;

import java.math.BigDecimal;

public record MoneyDto(BigDecimal amount, CurrencyDto currency) {

}
