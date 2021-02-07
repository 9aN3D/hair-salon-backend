package pl.edu.wit.application.domain.model.auth_details;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.domain.model.Password;
import pl.edu.wit.application.exception.auth_details.AuthDetailsPasswordNotValidException;
import pl.edu.wit.application.domain.model.StringNotBlank;

import static java.lang.String.format;

@EqualsAndHashCode
public class AuthDetailsPassword implements Password {

    private final String value;

    public AuthDetailsPassword(String value) {
        this.value = setValue(value);
    }

    @Override
    public String value() {
        return value;
    }

    private String setValue(String value) {
        var validatedValue = new StringNotBlank(value).validate();
        int passwordMinLength = 6;
        if (validatedValue.length() > passwordMinLength) {
            return validatedValue;
        }
        throw new AuthDetailsPasswordNotValidException(
                format("Auth details password is not valid, minimum length %d", passwordMinLength)
        );
    }

}
