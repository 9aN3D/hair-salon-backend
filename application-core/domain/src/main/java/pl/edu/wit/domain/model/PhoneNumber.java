package pl.edu.wit.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.exception.PhoneNumberNotValidException;

import static java.lang.String.format;

@ToString
@EqualsAndHashCode
public class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) {
        this.value = validateValue(value);
    }

    public String value() {
        return value;
    }

    private String validateValue(String value) {
        var validatedString = new StringNotBlank(value).validate();
        if (validatePhoneNumber(value)) {
            return validatedString;
        }
        throw new PhoneNumberNotValidException(format("Phone number %s is not valid", value));
    }

    private Boolean validatePhoneNumber(String value) {
        //TODO add com.googlecode.libphonenumber
        return true;//value.matches("\\^[+]*\\d{10}");
    }

}
