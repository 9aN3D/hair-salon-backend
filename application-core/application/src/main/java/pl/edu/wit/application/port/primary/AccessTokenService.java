package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.GenerateAccessTokenCommand;
import pl.edu.wit.application.command.RefreshAccessTokenCommand;
import pl.edu.wit.domain.dto.AccessTokenDto;

public interface AccessTokenService {

    AccessTokenDto generate(GenerateAccessTokenCommand command);

    AccessTokenDto refresh(RefreshAccessTokenCommand command);

}
