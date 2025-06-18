package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.dto.CurrencyDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.sharedKernel.domain.Currency.PLN;

public record Money(BigDecimal amount, Currency currency) {

    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.valueOf(CurrencyDto.PLN));

    public Money(BigDecimal amount, Currency currency) {
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
        var currency = Currency.valueOf(dto.currency());
        return new Money(dto.amount(), currency);
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
        return new MoneyDto(
                amount,
                currency.toDto()
        );
    }

}
