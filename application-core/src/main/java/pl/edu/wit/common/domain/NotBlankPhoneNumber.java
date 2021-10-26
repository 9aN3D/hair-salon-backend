package pl.edu.wit.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class NotBlankPhoneNumber implements PhoneNumber {

    private final String value;

    public NotBlankPhoneNumber(String value) {
        this.value = new NotBlankString(value).value();
    }

    @Override
    public String value() {
        return value;
    }

}
