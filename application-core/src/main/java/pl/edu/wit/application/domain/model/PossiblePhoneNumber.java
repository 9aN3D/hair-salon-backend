package pl.edu.wit.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.exception.ValidationException;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@ToString
@EqualsAndHashCode
public class PossiblePhoneNumber implements PhoneNumber {

    private final PhoneNumber phoneNumber;

    public PossiblePhoneNumber(PhoneNumber phoneNumber, PhoneNumberProvider provider) {
        this.phoneNumber = ofNullable(phoneNumber)
                .filter(isPossibleNumberPredicate(provider))
                .orElseThrow(() -> new ValidationException("Phone number is not possible"));
    }

    @Override
    public String value() {
        return phoneNumber.value();
    }

    private static Predicate<PhoneNumber> isPossibleNumberPredicate(PhoneNumberProvider phoneNumberProvider) {
        return number -> ofNullable(phoneNumberProvider)
                .map(provider -> provider.isPossibleNumber(number.value()))
                .orElse(false);
    }

}
