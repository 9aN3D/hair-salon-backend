package pl.edu.wit.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.PasswordEncoder;
import pl.edu.wit.auth_details.AuthDetailsFacade;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;
import pl.edu.wit.member.MemberFacade;
import pl.edu.wit.member.port.secondary.MemberRepository;
import pl.edu.wit.token.AccessTokenFacade;
import pl.edu.wit.token.port.secondary.AccessTokenProvider;

@Configuration
public class FacadeConfiguration {

    @Bean
    AuthDetailsFacade authDetailsFacade(IdGenerator idGenerator, PasswordEncoder passwordEncoder, AuthDetailsRepository repository) {
        return new AuthDetailsFacade(idGenerator, passwordEncoder, repository);
    }

    @Bean
    AccessTokenFacade accessTokenFacade(AccessTokenProvider accessTokenProvider) {
        return new AccessTokenFacade(accessTokenProvider);
    }

    @Bean
    MemberFacade memberFacade(IdGenerator idGenerator, MemberRepository repository, AuthDetailsFacade authDetailsFacade) {
        return new MemberFacade(idGenerator, repository, authDetailsFacade);
    }

}
