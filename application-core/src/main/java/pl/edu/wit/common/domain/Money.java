package pl.edu.wit.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.common.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Currency;

import static java.util.Optional.ofNullable;

@ToString
@EqualsAndHashCode
public class Money {

    private final BigDecimal amount;
    private final String currencyCode;
    private final Currency currency;

    public Money(BigDecimal amount, String currencyCode, Currency currency) {
        this.amount = ofNullable(amount).orElseThrow(() -> new ValidationException("Money amount cannot be null"));
        this.currencyCode = new NotBlankString(currencyCode).value();
        this.currency = ofNullable(currency).orElseThrow(() -> new ValidationException("Money currency cannot be null"));
    }

    public Money(BigDecimal amount, String currencyCode) {
        this(
                amount,
                currencyCode,
                Currency.getInstance(new NotBlankString(currencyCode).value())
        );
    }

    public Money(BigDecimal amount) {
        this(amount, "PLN");
    }

    public BigDecimal amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    public Boolean hasPositiveAmount() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

}
