package pl.edu.wit.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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
        return new StringNotBlank(value).validate();
    }

}
