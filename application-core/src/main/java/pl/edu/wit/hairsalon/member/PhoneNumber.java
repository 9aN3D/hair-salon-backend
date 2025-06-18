package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

record PhoneNumber(String value) implements SelfValidator<PhoneNumber> {

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
