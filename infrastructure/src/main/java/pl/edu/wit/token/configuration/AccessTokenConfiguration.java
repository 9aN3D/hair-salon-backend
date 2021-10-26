package pl.edu.wit.token.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.edu.wit.token.port.primary.AccessTokenService;
import pl.edu.wit.token.service.AppAccessTokenService;

@Configuration
public class AccessTokenConfiguration {

    @ConditionalOnMissingBean(AccessTokenService.class)
    @Import(AppAccessTokenService.class)
    @Configuration
    static class AccessTokenServiceConfiguration {

    }

}
