package pl.edu.wit.hairsalon.sharedkernel.domain;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedkernel.dto.CurrencyDto;

import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_UP;

@RequiredArgsConstructor
public enum Currency {

    PLN;

    private final int scale = 2;
    private final RoundingMode roundingMode = HALF_UP;

    int scale() {
        return scale;
    }

    RoundingMode roundingMode() {
        return roundingMode;
    }

    CurrencyDto toDto() {
        return CurrencyDto.valueOf(this.name());
    }

    static Currency valueOf(CurrencyDto dto) {
        return Currency.valueOf(dto.name());
    }

}
