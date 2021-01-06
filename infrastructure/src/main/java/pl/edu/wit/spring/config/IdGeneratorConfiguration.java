package pl.edu.wit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.spring.adapter.id.generator.MongoIdGenerator;

@Configuration
public class IdGeneratorConfiguration {

    @Bean
    IdGenerator idGenerator() {
        return new MongoIdGenerator();
    }

}
