package pl.edu.wit.hairsalon.authDetails;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@Configuration(proxyBeanMethods = false)
class AuthDetailsConfiguration {

    @Bean
    AuthDetailsFacade authDetailsFacade(AuthDetailsPort authDetailsPort,
                                        IdGenerator idGenerator,
                                        PasswordEncoderPort passwordEncoderPort) {
        var creator = new AuthDetailsCreator(idGenerator, authDetailsPort, passwordEncoderPort);
        var updater = new AuthDetailsUpdater(authDetailsPort, passwordEncoderPort);
        var remover = new AuthDetailsRemover(authDetailsPort);
        return new LoggableAuthDetailsFacade(
                new AppAuthDetailsFacade(authDetailsPort, creator, updater, remover)
        );
    }

}
