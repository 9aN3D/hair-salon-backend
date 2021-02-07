package pl.edu.wit.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.exception.PhoneNumberNotValidException;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import static java.lang.String.format;

@ToString
@EqualsAndHashCode
public class PossiblePhoneNumber implements PhoneNumber {

    private final PhoneNumber phoneNumber;

    public PossiblePhoneNumber(PhoneNumber phoneNumber, PhoneNumberProvider provider) {
        this.phoneNumber = setPhoneNumber(phoneNumber, provider);
    }

    @Override
    public String value() {
        return phoneNumber.value();
    }

    private PhoneNumber setPhoneNumber(PhoneNumber phoneNumber, PhoneNumberProvider provider) {
        if (provider.isPossibleNumber(phoneNumber.value())) {
            return phoneNumber;
        }
        throw new PhoneNumberNotValidException(format("Phone number %s is not valid", phoneNumber.value()));
    }

}
