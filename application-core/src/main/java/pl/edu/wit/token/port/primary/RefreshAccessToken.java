package pl.edu.wit.token.port.primary;

import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.TokenDto;

public interface RefreshAccessToken {

    TokenDto refresh(RefreshAccessTokenCommand command);

}
