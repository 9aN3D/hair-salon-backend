package pl.edu.wit.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class NotBlankPhoneNumber implements PhoneNumber {

    private final String value;

    public NotBlankPhoneNumber(String value) {
        this.value = setValue(value);
    }

    @Override
    public String value() {
        return value;
    }

    private String setValue(String value) {
        return new StringNotBlank(value).validate();
    }

}
