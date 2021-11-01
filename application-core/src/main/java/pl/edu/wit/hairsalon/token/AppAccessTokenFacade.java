package pl.edu.wit.hairsalon.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppAccessTokenFacade implements AccessTokenFacade {

    private final AccessTokenPort accessTokenPort;

    @Override
    public AccessTokenDto generate(AccessTokenGenerateCommand command) {
        log.trace("Generating access token {command: {}}", command);
        requireNonNull(command, "Access token generate command must not be null");
        var accessTokenDto = accessTokenPort.generate(command.getEmail(), command.getPassword());
        log.info("Generated access token {accessToken: {}}", accessTokenDto);
        return accessTokenDto;
    }

    @Override
    public AccessTokenDto refresh(AccessTokenRefreshCommand command) {
        log.trace("Refreshing access token {command: {}", command);
        requireNonNull(command, "Access token refresh command must not be null");
        var accessTokenDto = accessTokenPort.refresh(command.getRefreshToken());
        log.info("Refreshed access token {accessToken: {}}", accessTokenDto);
        return accessTokenDto;
    }

}
