package pl.edu.wit.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.edu.wit.auth_details.port.primary.CreateAuthDetails;
import pl.edu.wit.auth_details.port.primary.FindAuthDetails;
import pl.edu.wit.auth_details.usecase.AuthDetailsUseCase;
import pl.edu.wit.auth_details.usecase.FindAuthDetailsUseCase;

@Configuration
public class AuthDetailsConfiguration {

    @Configuration
    @ConditionalOnMissingBean(CreateAuthDetails.class)
    @Import(AuthDetailsUseCase.class)
    public static class CreateAuthDetailsConfiguration {

    }

    @Configuration
    @ConditionalOnMissingBean(FindAuthDetails.class)
    @Import(FindAuthDetailsUseCase.class)
    public static class FindAuthDetailsConfiguration {

    }

}
