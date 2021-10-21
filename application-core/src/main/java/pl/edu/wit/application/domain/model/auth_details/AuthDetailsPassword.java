package pl.edu.wit.application.domain.model.auth_details;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.domain.model.Password;
import pl.edu.wit.application.exception.ValidationException;

import java.util.Optional;

import static java.lang.String.format;

@ToString
@EqualsAndHashCode
public class AuthDetailsPassword implements Password {

    private final String value;
    private final Integer passwordMinLength = 6;

    public AuthDetailsPassword(String value) {
        this.value = notBlankValue(value)
                .orElseThrow(() -> new ValidationException(
                        format("Auth details password %s is not valid, minimum length %d", value, passwordMinLength)
                ));
    }

    @Override
    public String value() {
        return value;
    }

    private Optional<String> notBlankValue(String value) {
        return new NotBlankString(value)
                .filter(this::hasNotSpace)
                .filter(this::isValid);
    }

    private Boolean hasNotSpace(String value) {
        return !value.contains(" ");
    }

    private Boolean isValid(String value) {
        return value.length() > passwordMinLength;
    }

}
