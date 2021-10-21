package pl.edu.wit.application.domain.model;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotBlankPhoneNumberTest {

    @Test
    void shouldCreateNotBlankPhoneNumberWhenValueIsNotBlank() {
        var value = "test value";

        var notBlankPhoneNumber = new NotBlankPhoneNumber(value);
        assertEquals(value, notBlankPhoneNumber.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new NotBlankPhoneNumber(null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new NotBlankPhoneNumber("  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

}
