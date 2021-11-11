package pl.edu.wit.hairsalon.sharedkernel.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.dto.CurrencyDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.MoneyDto;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.sharedkernel.domain.Currency.PLN;

@ToString
@EqualsAndHashCode
public class Money {

    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.valueOf(CurrencyDto.PLN));

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        this.currency = requireNonNull(currency, "Money amount must not be null");
        this.amount = requireNonNull(amount, "Money amount must not be null").setScale(this.currency.scale(), this.currency.roundingMode());
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    public boolean hasNotPositiveAmount() {
        return !hasPositiveAmount();
    }

    public boolean hasPositiveAmount() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static Money of(MoneyDto dto) {
        var currency = Currency.valueOf(dto.getCurrency());
        return new Money(dto.getAmount(), currency);
    }

    public static Money ofPln(BigDecimal amount) {
        return new Money(amount, PLN);
    }

    public Money add(Money arg) {
        requireNonNull(arg, "Money must no be null");
        if (!arg.currency().equals(currency)) {
            throw new ValidationException("Currency not equals");
        }
        return new Money(amount.add(arg.amount), currency);
    }

    public MoneyDto toDto() {
        return MoneyDto.builder()
                .amount(amount)
                .currency(currency.toDto())
                .build();
    }

}
