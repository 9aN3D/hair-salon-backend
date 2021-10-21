package pl.edu.wit.application.domain.model;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.exception.ValidationException;
import pl.edu.wit.application.port.secondary.MockPasswordEncoder;
import pl.edu.wit.application.port.secondary.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncodedPasswordTest {

    private final PasswordEncoder passwordEncoder = new MockPasswordEncoder();

    @Test
    void shouldCreateEncodedPasswordWhenValueIsNotBlank() {
        var value = "test value";

        var encodedPassword = new EncodedPassword(value);
        assertEquals(value, encodedPassword.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new EncodedPassword(null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new EncodedPassword("  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldCreateEncodedPasswordWhenValueIsNotBlankAndUseMockPasswordEncoder() {
        var value = "test value";

        var encodedPassword = new EncodedPassword(() -> value, passwordEncoder);
        assertNotEquals(value, encodedPassword.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenPasswordEncoderIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new EncodedPassword(() -> "test value", null);
        });
        assertEquals("Password is not encode", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPasswordEncoderReturnEmptyString() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new EncodedPassword(() -> "test value", value -> " ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

}
