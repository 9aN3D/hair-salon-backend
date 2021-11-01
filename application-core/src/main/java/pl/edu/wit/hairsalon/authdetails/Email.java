package pl.edu.wit.hairsalon.authdetails;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.util.regex.Pattern;

import static java.lang.String.format;

@ToString(of = "value")
@RequiredArgsConstructor
@EqualsAndHashCode(of = "value")
class Email implements SelfValidator<Email> {

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String value;

    @Override
    public Email validate() {
        validate(new NotBlankString(value));
        if (!hasMatchPattern(value)) {
            throw new ValidationException(
                    format("Email %s is not valid", value));
        }
        return this;
    }

    String value() {
        return value;
    }

    private Boolean hasMatchPattern(String value) {
        return Pattern.compile(regex).matcher(value).matches();
    }

}
