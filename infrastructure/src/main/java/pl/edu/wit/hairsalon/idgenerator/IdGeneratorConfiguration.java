package pl.edu.wit.hairsalon.idgenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class IdGeneratorConfiguration {

    @Bean
    IdGenerator idGenerator() {
        return new MongoIdGenerator();
    }

}
