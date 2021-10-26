package pl.edu.wit.id.generator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.common.port.secondary.IdGenerator;
import pl.edu.wit.id.generator.adapter.MongoIdGenerator;

@Configuration
public class IdGeneratorConfiguration {

    @Bean
    IdGenerator idGenerator() {
        return new MongoIdGenerator();
    }

}
