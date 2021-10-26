package pl.edu.wit.common.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.domain.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void shouldCreateEmailWhenValueIsEmail() {
        var value = "tes@gmail.com";

        var notBlankString = new Email(value);
        assertEquals(value, notBlankString.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNotEmail() {
        var value = "test";

        var validationException = assertThrows(ValidationException.class, () -> {
            new Email(value);
        });
        assertEquals("Email test is not valid", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Email(null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Email("  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

}
