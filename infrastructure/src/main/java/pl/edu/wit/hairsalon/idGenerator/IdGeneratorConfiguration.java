package pl.edu.wit.hairsalon.idGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@Configuration(proxyBeanMethods = false)
class IdGeneratorConfiguration {

    @Bean
    IdGenerator idGenerator() {
        return new MongoIdGenerator();
    }

}
