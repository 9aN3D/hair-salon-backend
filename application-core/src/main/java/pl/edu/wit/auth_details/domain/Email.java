package pl.edu.wit.auth_details.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.auth_details.shared.exception.EmailNotValidException;
import pl.edu.wit.common.StringNotBlank;

import java.util.regex.Pattern;

import static java.lang.String.format;

@ToString(of = "email")
@EqualsAndHashCode(of = "email")
class Email {

    private final Pattern pattern;
    private final String email;

    public Email(String value) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        this.pattern = Pattern.compile(regex);
        this.email = validateEmail(value);
    }

    public String validateEmail(String value) {
        var validatedString = new StringNotBlank(value).validate();
        if (pattern.matcher(validatedString).matches()) {
            return validatedString;
        }
        throw new EmailNotValidException(format("Email %s is not valid", email));
    }

    public String value() {
        return email;
    }

}
