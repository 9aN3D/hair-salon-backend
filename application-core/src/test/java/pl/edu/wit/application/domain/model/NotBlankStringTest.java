package pl.edu.wit.application.domain.model;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.exception.ValidationException;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotBlankStringTest {

    @Test
    void shouldCreateNotBlankStringWhenValueIsNotBlank() {
        var value = "test value";

        var notBlankString = new NotBlankString(value);
        assertEquals(value, notBlankString.value());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new NotBlankString(null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new NotBlankString("  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldFilterNotBlankStringValue() {
        var value = "test value";
        var maybeNotBlankString = new NotBlankString(value).filter(String::isBlank);
        assertEquals(of(value).filter(String::isBlank), maybeNotBlankString);
    }

}
