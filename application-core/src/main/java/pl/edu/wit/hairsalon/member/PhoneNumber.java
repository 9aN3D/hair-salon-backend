package pl.edu.wit.hairsalon.member;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class PhoneNumber implements SelfValidator<PhoneNumber> {

    private final String value;

    public String value() {
        return value;
    }

    @Override
    public PhoneNumber validate() {
        validate(new NotBlankString(value));
        return this;
    }

    public PhoneNumber verifyPhoneNumber(PhoneNumberPort phoneNumberPort) {
        requireNonNull(phoneNumberPort, "Phone number port is not null");
        if (!phoneNumberPort.isPossibleNumber(value)) {
            throw new ValidationException(format("Phone number is not possible %s", value));
        }
        return this;
    }

}
