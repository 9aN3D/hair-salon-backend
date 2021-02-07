package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.AccessTokenGenerateCommand;
import pl.edu.wit.application.command.AccessTokenRefreshCommand;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.application.port.secondary.AccessTokenProvider;
import pl.edu.wit.application.dto.AccessTokenDto;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.StringNotBlank;

@Slf4j
@RequiredArgsConstructor
public class AppAccessTokenService implements AccessTokenService {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public AccessTokenDto generate(AccessTokenGenerateCommand command) {
        log.trace("Generating access token {command: {}}", command);
        var validEmail = new Email(command.getEmail()).value();
        var validPassword = new StringNotBlank(command.getPassword()).validate();
        return accessTokenProvider.generate(validEmail, validPassword);
    }

    @Override
    public AccessTokenDto refresh(AccessTokenRefreshCommand command) {
        log.trace("Refreshing access token {command: {}", command);
        var value = new StringNotBlank(command.getRefreshToken()).validate();
        return accessTokenProvider.refresh(value);
    }

}
