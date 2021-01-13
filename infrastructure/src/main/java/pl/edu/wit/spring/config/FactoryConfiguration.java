package pl.edu.wit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.application.factory.AuthDetailsFactory;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PasswordEncoder;

@Configuration
public class FactoryConfiguration {

    @Bean
    AuthDetailsFactory authDetailsFactory(IdGenerator idGenerator, PasswordEncoder passwordEncoder) {
        return new AuthDetailsFactory(idGenerator, passwordEncoder);
    }

    @Bean
    MemberFactory memberFactory(IdGenerator idGenerator, AuthDetailsFactory authDetailsFactory) {
        return new MemberFactory(idGenerator, authDetailsFactory);
    }

}
