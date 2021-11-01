package pl.edu.wit.hairsalon.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenConfiguration {

    @Bean
    AccessTokenFacade accessTokenFacade(AccessTokenPort accessTokenPort) {
        return new AppAccessTokenFacade(accessTokenPort);
    }

}
