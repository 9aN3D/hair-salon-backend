package pl.edu.wit.hairdresser.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.hairdresser.port.primary.HairdresserService;
import pl.edu.wit.hairdresser.service.AppHairdresserService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.hairdresser.adapter")
public class HairdresserConfiguration {

    @ConditionalOnMissingBean(HairdresserService.class)
    @Import(AppHairdresserService.class)
    @Configuration
    static class HairdresserServiceConfiguration {

    }

}
