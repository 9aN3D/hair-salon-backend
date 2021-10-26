package pl.edu.wit.auth_details.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.auth.details.domain.AuthDetailsPassword;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthDetailsPasswordTest {

    @Test
    void shouldCreateAuthDetailsPasswordWhenValueIsNotBlank() {
        var value = "test_value";

        var authDetailsPassword = new AuthDetailsPassword(value);
        assertEquals(value, authDetailsPassword.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetailsPassword(null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetailsPassword("  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueHasSpace() {
        var value = "test value";
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetailsPassword(value);
        });
        assertEquals(format("Auth details password %s is not valid, minimum length 6", value), validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueLengthLessThanMinimumLength() {
        var value = "test";
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetailsPassword(value);
        });
        assertEquals(format("Auth details password %s is not valid, minimum length 6", value), validationException.getMessage());
    }

}
