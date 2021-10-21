package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.token.AccessTokenGenerateCommand;
import pl.edu.wit.application.command.token.AccessTokenRefreshCommand;
import pl.edu.wit.application.domain.model.AccessToken;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.dto.AccessTokenDto;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.application.port.secondary.AccessTokenProvider;

@Slf4j
@RequiredArgsConstructor
public class AppAccessTokenService implements AccessTokenService {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public AccessTokenDto generate(AccessTokenGenerateCommand command) {
        log.trace("Generating access token {command: {}}", command);
        var email = new Email(command.getEmail()).value();
        var password = new NotBlankString(command.getPassword()).value();
        var accessToken = new AccessToken(accessTokenProvider.generate(email, password));
        log.trace("Generated access token {accessToken: {}}", accessToken);
        return accessToken.toDto();
    }

    @Override
    public AccessTokenDto refresh(AccessTokenRefreshCommand command) {
        log.trace("Refreshing access token {command: {}", command);
        var value = new NotBlankString(command.getRefreshToken()).value();
        var accessToken = new AccessToken(accessTokenProvider.refresh(value));
        log.trace("Refreshed access token {accessToken: {}}", accessToken);
        return accessToken.toDto();
    }

}
