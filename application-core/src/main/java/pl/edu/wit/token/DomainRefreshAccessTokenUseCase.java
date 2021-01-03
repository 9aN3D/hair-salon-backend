package pl.edu.wit.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.token.port.primary.RefreshAccessTokenUseCase;
import pl.edu.wit.token.port.secondary.AccessTokenProvider;
import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.AccessTokenDto;

@Slf4j
@RequiredArgsConstructor
class DomainRefreshAccessTokenUseCase implements RefreshAccessTokenUseCase {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public AccessTokenDto refresh(RefreshAccessTokenCommand command) {
        log.trace("Refreshing access token {command: {}", command);
        var value = new StringNotBlank(command.getRefreshToken()).validate();
        return accessTokenProvider.refresh(value);
    }

}
