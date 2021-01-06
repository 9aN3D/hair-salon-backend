package pl.edu.wit.domain.model.auth_details;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.exception.auth_details.AuthDetailsPasswordNotValidException;
import pl.edu.wit.domain.model.StringNotBlank;

import static java.lang.String.format;

@ToString
@EqualsAndHashCode
public class AuthDetailsPassword {

    private final String password;

    public AuthDetailsPassword(String value) {
        this.password = setPassword(value);
    }

    private String setPassword(String value) {
        var validatedValue = new StringNotBlank(value).validate();
        int passwordMinLength = 6;
        if (validatedValue.length() > passwordMinLength) {
            return validatedValue;
        }
        throw new AuthDetailsPasswordNotValidException(
                format("Auth details password is not valid, minimum length %d", passwordMinLength)
        );
    }

    public String value() {
        return password;
    }

}
