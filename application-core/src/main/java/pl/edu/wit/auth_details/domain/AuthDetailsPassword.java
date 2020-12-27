package pl.edu.wit.auth_details.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsPasswordNotValidException;
import pl.edu.wit.common.StringNotBlank;

import static java.lang.String.format;

@ToString
@EqualsAndHashCode
class AuthDetailsPassword {

    private final String password;

    public AuthDetailsPassword(String value) {
        this.password = validatePassword(value);
    }

    private String validatePassword(String value) {
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
