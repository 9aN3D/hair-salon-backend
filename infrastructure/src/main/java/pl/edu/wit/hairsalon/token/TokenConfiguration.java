package pl.edu.wit.hairsalon.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class TokenConfiguration {

    @Bean
    AccessTokenFacade accessTokenFacade(AccessTokenPort accessTokenPort) {
        return new LoggableAccessTokenFacade(
                new AppAccessTokenFacade(accessTokenPort)
        );
    }

}
