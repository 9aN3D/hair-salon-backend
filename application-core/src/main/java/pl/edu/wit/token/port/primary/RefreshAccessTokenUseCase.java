package pl.edu.wit.token.port.primary;

import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.AccessTokenDto;

public interface RefreshAccessTokenUseCase {

    AccessTokenDto refresh(RefreshAccessTokenCommand command);

}
