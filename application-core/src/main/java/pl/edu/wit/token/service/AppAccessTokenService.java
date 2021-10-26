package pl.edu.wit.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.token.domain.AccessToken;
import pl.edu.wit.common.domain.Email;
import pl.edu.wit.common.domain.NotBlankString;
import pl.edu.wit.token.dto.AccessTokenDto;
import pl.edu.wit.token.port.primary.AccessTokenService;
import pl.edu.wit.token.port.secondary.AccessTokenProvider;

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
