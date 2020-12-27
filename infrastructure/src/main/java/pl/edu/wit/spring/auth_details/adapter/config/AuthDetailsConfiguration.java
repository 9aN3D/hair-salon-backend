package pl.edu.wit.spring.auth_details.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.auth_details.usecase.AuthDetailsService;

@Configuration
public class AuthDetailsConfiguration {

    @Bean
    AuthDetailsService authDetailsService(IdGenerator idGenerator, AuthDetailsRepository authDetailsRepository) {
        return new AuthDetailsService(idGenerator, authDetailsRepository);
    }

}
