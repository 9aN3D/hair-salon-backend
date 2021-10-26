package pl.edu.wit.common.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.domain.Money;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {

    @Test
    void shouldCreateMoneyWhenValueIsTwoUnit() {
        var value = BigDecimal.valueOf(2);
        var currency = Currency.getInstance("PLN");

        var money = new Money(value);
        assertEquals(value, money.amount());
        assertEquals(currency, money.currency());
    }

    @Test
    void shouldHasPositiveAmountMoneyWhenAmountEqualsTwoUnit() {
        var value = BigDecimal.valueOf(2);

        var money = new Money(value);
        assertTrue(money.hasPositiveAmount());
    }

    @Test
    void shouldHasNegativeAmountMoneyWhenAmountEqualsZero() {
        var value = BigDecimal.ZERO;

        var money = new Money(value);
        assertFalse(money.hasPositiveAmount());
    }

    @Test
    void shouldThrowValidationExceptionWhenAmountIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Money(null);
        });
        assertEquals("Money amount cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenCurrencyCodeIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Money(BigDecimal.ZERO, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenCurrencyCodeIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Money(BigDecimal.ZERO, "  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

}
