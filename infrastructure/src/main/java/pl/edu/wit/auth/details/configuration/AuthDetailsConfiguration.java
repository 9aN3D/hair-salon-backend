package pl.edu.wit.auth.details.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.auth.details.port.primary.AuthDetailsService;
import pl.edu.wit.auth.details.service.AppAuthDetailsService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.auth.details.adapter")
public class AuthDetailsConfiguration {

    @ConditionalOnMissingBean(AuthDetailsService.class)
    @Import(AppAuthDetailsService.class)
    @Configuration
    static class AuthDetailsServiceConfiguration {

    }

}
