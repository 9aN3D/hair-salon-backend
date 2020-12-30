package pl.edu.wit.token.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.token.port.primary.RefreshAccessToken;
import pl.edu.wit.token.port.secondary.TokenProvider;
import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.TokenDto;

@Slf4j
@RequiredArgsConstructor
public class RefreshAccessTokenUseCase implements RefreshAccessToken {

    private final TokenProvider tokenProvider;

    @Override
    public TokenDto refresh(RefreshAccessTokenCommand command) {
        log.trace("Refreshing access token {command: {}", command);
        var value = new StringNotBlank(command.getRefreshToken()).validate();
        return tokenProvider.refresh(value);
    }

}
