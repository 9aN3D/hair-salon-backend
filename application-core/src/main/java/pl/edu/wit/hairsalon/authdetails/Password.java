package pl.edu.wit.hairsalon.authdetails;

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
class Password implements SelfValidator<Password> {

    private final String value;
    private final Integer passwordMinLength = 6;

    public String value() {
        return value;
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
