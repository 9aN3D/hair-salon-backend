package pl.edu.wit.hairsalon.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.authdetails.AuthDetailsFacade;

@Configuration
class UserConfiguration {

    @Bean
    UserFacade userFacade(UserPort userPort, AuthDetailsFacade authDetailsFacade) {
        var creator = new UserCreator(userPort, authDetailsFacade);
        var updater = new UserUpdater(userPort, authDetailsFacade);
        return new AppUserFacade(userPort, creator, updater);
    }

}
