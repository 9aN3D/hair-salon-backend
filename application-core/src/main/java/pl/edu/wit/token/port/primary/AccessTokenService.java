package pl.edu.wit.token.port.primary;

import pl.edu.wit.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.token.dto.AccessTokenDto;

public interface AccessTokenService {

    AccessTokenDto generate(AccessTokenGenerateCommand command);

    AccessTokenDto refresh(AccessTokenRefreshCommand command);

}
