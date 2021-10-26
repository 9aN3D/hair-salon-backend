package pl.edu.wit.user.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.user.port.primary.UserService;
import pl.edu.wit.user.service.AppUserService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.user.adapter")
public class UserConfiguration {

    @ConditionalOnMissingBean(UserService.class)
    @Import(AppUserService.class)
    @Configuration
    static class UserServiceConfiguration {

    }

}
