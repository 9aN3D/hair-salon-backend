package pl.edu.wit.hairsalon.passwordEncoder;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.authDetails.PasswordEncoderPort;

@Component
@RequiredArgsConstructor
class SpringBCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

}
