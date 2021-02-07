package pl.edu.wit.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.port.secondary.PasswordEncoder;

@ToString
@EqualsAndHashCode
public class EncodedPassword implements Password {

    private final String value;

    public EncodedPassword(Password password, PasswordEncoder passwordEncoder) {
        this.value = setPassword(password, passwordEncoder);
    }

    @Override
    public String value() {
        return value;
    }

    private String setPassword(Password password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password.value());
    }

}
