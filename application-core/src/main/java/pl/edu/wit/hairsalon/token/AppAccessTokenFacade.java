package pl.edu.wit.hairsalon.token;

import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

import static java.util.Objects.requireNonNull;

class AppAccessTokenFacade implements AccessTokenFacade {

    private final AccessTokenPort accessTokenPort;

    AppAccessTokenFacade(AccessTokenPort accessTokenPort) {
        this.accessTokenPort = accessTokenPort;
    }

    @Override
    public AccessTokenDto generate(AccessTokenGenerateCommand command) {
        requireNonNull(command, "Access token generate command must not be null");
        return accessTokenPort.generate(command.email(), command.password());
    }

    @Override
    public AccessTokenDto refresh(AccessTokenRefreshCommand command) {
        requireNonNull(command, "Access token refresh command must not be null");
        return accessTokenPort.refresh(command.refreshToken());
    }
}
