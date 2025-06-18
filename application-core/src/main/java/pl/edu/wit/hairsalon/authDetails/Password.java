package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

record Password(String value, Integer passwordMinLength) implements SelfValidator<Password> {

    Password(String value) {
        this(value, 6);
    }

    @Override
    public Password validate() {
        validate(new NotBlankString(value));
        if (isNotValidMinLength(value)) {
            throw new ValidationException(
                    format("Auth details password %s is not valid, minimum length %d", value, passwordMinLength)
            );
        }
        return this;
    }

    public Password encode(PasswordEncoderPort passwordEncoderPort) {
        requireNonNull(passwordEncoderPort, "Password encoder port must not be null");
        return new Password(passwordEncoderPort.encode(value));
    }

    private Boolean isNotValidMinLength(String value) {
        return value.length() < passwordMinLength;
    }

}
