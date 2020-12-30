package pl.edu.wit.token.port.primary;

import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.dto.TokenDto;

public interface GenerateAccessToken {

    TokenDto generate(GenerateAccessTokenCommand command);

}
