package pl.edu.wit.hairsalon.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

class LoggableAccessTokenFacade implements AccessTokenFacade {

    private final Logger log;
    private final AccessTokenFacade delegate;

    LoggableAccessTokenFacade(AccessTokenFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableAccessTokenFacade.class);
        this.delegate = delegate;
    }

    @Override
    public AccessTokenDto generate(AccessTokenGenerateCommand command) {
        log.trace("Generating access token {command: {}}", command);
        var result = delegate.generate(command);
        log.info("Generated access token {accessToken: {}}", result);
        return result;
    }

    @Override
    public AccessTokenDto refresh(AccessTokenRefreshCommand command) {
        log.trace("Refreshing access token {command: {}}", command);
        var result = delegate.refresh(command);
        log.info("Refreshed access token {accessToken: {}}", result);
        return result;
    }

}
