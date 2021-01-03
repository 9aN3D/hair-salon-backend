package pl.edu.wit.member.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.member.shared.exception.PhoneNumberNotValidException;

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
        return value.matches("\\d{10}")
                //validating phone number with -, . or spaces
                || value.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
                //validating phone number with extension length from 3 to 5
                || value.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
                //validating phone number where area code is in braces ()
                || value.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}");
    }

}
