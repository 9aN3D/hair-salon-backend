package pl.edu.wit.token.port.primary;

import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.dto.AccessTokenDto;

public interface GenerateAccessTokenUseCase {

    AccessTokenDto generate(GenerateAccessTokenCommand command);

}
