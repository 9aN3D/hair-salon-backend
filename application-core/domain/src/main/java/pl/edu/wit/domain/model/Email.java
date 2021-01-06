package pl.edu.wit.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.exception.EmailNotValidException;

import java.util.regex.Pattern;

import static java.lang.String.format;

@ToString(of = "email")
@EqualsAndHashCode(of = "email")
public class Email {

    private final Pattern pattern;
    private final String email;

    public Email(String value) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        this.pattern = Pattern.compile(regex);
        this.email = validateEmail(value);
    }

    public String value() {
        return email;
    }

    private String validateEmail(String value) {
        var validatedString = new StringNotBlank(value).validate();
        if (pattern.matcher(validatedString).matches()) {
            return validatedString;
        }
        throw new EmailNotValidException(format("Email %s is not valid", value));
    }

}
