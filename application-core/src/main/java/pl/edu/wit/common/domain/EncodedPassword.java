package pl.edu.wit.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.port.secondary.PasswordEncoder;

import static java.util.Optional.ofNullable;

@ToString
@EqualsAndHashCode
public class EncodedPassword implements Password {

    private final NotBlankString notBlankValue;

    public EncodedPassword(String value) {
        this.notBlankValue = new NotBlankString(value);
    }

    public EncodedPassword(Password password, PasswordEncoder passwordEncoder) {
        this(ofNullable(passwordEncoder)
                .map(encoder -> encoder.encode(password.value()))
                .orElseThrow(() -> new ValidationException("Password is not encode"))
        );
    }

    @Override
    public String value() {
        return notBlankValue.value();
    }

}
