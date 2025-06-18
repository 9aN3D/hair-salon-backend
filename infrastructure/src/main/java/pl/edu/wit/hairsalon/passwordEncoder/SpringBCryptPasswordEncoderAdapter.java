package pl.edu.wit.hairsalon.passwordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.authDetails.PasswordEncoderPort;

@Component
class SpringBCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    SpringBCryptPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

}
