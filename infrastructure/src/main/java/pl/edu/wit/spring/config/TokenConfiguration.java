package pl.edu.wit.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.edu.wit.token.port.primary.GenerateAccessToken;
import pl.edu.wit.token.port.primary.RefreshAccessToken;
import pl.edu.wit.token.usecase.GenerateAccessTokenUseCase;
import pl.edu.wit.token.usecase.RefreshAccessTokenUseCase;

@Configuration
public class TokenConfiguration {

    @Configuration
    @ConditionalOnMissingBean(GenerateAccessToken.class)
    @Import(GenerateAccessTokenUseCase.class)
    public static class GenerateAccessTokenConfiguration {

    }

    @Configuration
    @ConditionalOnMissingBean(RefreshAccessToken.class)
    @Import(RefreshAccessTokenUseCase.class)
    public static class RefreshAccessTokenConfiguration {

    }

}
