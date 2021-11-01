package pl.edu.wit.hairsalon.sharedkernel.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.dto.MoneyDto;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;
import static pl.edu.wit.hairsalon.sharedkernel.domain.Currency.PLN;

@ToString
@EqualsAndHashCode
public class Money {

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.currency = Objects.requireNonNull(currency, "Money amount must not be null");
        this.amount = Objects.requireNonNull(amount, "Money amount must not be null").setScale(this.currency.scale(), this.currency.roundingMode());
    }

    public BigDecimal amount() {
        return amount;
    }

    public boolean hasNotPositiveAmount() {
        return !hasPositiveAmount();
    }

    public boolean hasPositiveAmount() {
        return amount.compareTo(ZERO) > 0;
    }

    public static Money of(MoneyDto dto) {
        var currency = Currency.valueOf(dto.getCurrency());
        return new Money(dto.getAmount(), currency);
    }

    public static Money ofPln(BigDecimal amount) {
        return new Money(amount, PLN);
    }

    public MoneyDto toDto() {
        return MoneyDto.builder()
                .amount(amount)
                .currency(currency.toDto())
                .build();
    }

}
