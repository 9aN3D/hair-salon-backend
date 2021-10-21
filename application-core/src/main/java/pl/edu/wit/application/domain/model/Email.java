package pl.edu.wit.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.exception.ValidationException;

import java.util.regex.Pattern;

import static java.lang.String.format;

@ToString(of = "value")
@EqualsAndHashCode(of = "value")
public class Email {

    private final Pattern pattern;
    private final String value;

    public Email(String value) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        this.pattern = Pattern.compile(regex);
        this.value = new NotBlankString(value)
                .filter(this::hasMatchPattern)
                .orElseThrow(() -> new ValidationException(
                        format("Email %s is not valid", value))
                );
    }

    public String value() {
        return value;
    }

    private Boolean hasMatchPattern(String value) {
        return pattern.matcher(value).matches();
    }

}
