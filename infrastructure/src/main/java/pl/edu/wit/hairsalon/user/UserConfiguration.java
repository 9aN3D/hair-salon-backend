package pl.edu.wit.hairsalon.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;

@Configuration(proxyBeanMethods = false)
class UserConfiguration {

    @Bean
    UserFacade userFacade(UserPort userPort, AuthDetailsFacade authDetailsFacade) {
        var creator = new UserCreator(userPort, authDetailsFacade);
        var updater = new UserUpdater(userPort, authDetailsFacade);
        return new LoggableUserFacade(
                new AppUserFacade(userPort, creator, updater)
        );
    }

}
