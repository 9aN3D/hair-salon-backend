package pl.edu.wit.spring.adapter.password.encoder;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringBCryptPasswordEncoder implements pl.edu.wit.application.port.secondary.PasswordEncoder {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

}
