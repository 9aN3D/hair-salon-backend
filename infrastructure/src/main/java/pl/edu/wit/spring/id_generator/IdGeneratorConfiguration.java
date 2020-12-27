package pl.edu.wit.spring.id_generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.IdGenerator;

@Configuration
public class IdGeneratorConfiguration {

    @Bean
    IdGenerator idGenerator() {
        return new MongoIdGenerator();
    }

}
