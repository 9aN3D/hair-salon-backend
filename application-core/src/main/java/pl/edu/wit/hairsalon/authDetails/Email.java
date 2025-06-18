package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.regex.Pattern;

import static java.lang.String.format;

record Email(String value) implements SelfValidator<Email> {

    private final static String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public Email validate() {
        validate(new NotBlankString(value));
        if (!hasMatchPattern(value)) {
            throw new ValidationException(
                    format("Email %s is not valid", value));
        }
        return this;
    }

    private Boolean hasMatchPattern(String value) {
        return Pattern.compile(regex).matcher(value).matches();
    }

}
