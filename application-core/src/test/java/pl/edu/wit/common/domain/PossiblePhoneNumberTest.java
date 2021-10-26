package pl.edu.wit.common.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.port.secondary.PhoneNumberProvider;
import pl.edu.wit.common.domain.PossiblePhoneNumber;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PossiblePhoneNumberTest {

    @Test
    void shouldCreatePossiblePhoneNumberWhenValueIsPossibleNumber() {
        var value = "test value";

        var possiblePhoneNumber = new PossiblePhoneNumber(() -> value, getPhoneNumberProvider(true));

        assertEquals(value, possiblePhoneNumber.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenPhoneNumberIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new PossiblePhoneNumber(null, getPhoneNumberProvider(null));
        });
        assertEquals("Phone number is not possible", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPhoneNumberValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new PossiblePhoneNumber(() -> null, getPhoneNumberProvider(null));
        });
        assertEquals("Phone number is not possible", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenProviderPossibleNumberValueIsNull() {
        var value = "test value";
        var validationException = assertThrows(ValidationException.class, () -> {
            new PossiblePhoneNumber(() -> value, getPhoneNumberProvider(null));
        });
        assertEquals("Phone number is not possible", validationException.getMessage());
    }

    private PhoneNumberProvider getPhoneNumberProvider(Boolean value) {
        return new PhoneNumberProvider() {
            @Override
            public Boolean isPossibleNumber(String phoneNumber) {
                return value;
            }

            @Override
            public Boolean isPossibleNumber(String phoneNumber, Locale locale) {
                return value;
            }
        };
    }

}
