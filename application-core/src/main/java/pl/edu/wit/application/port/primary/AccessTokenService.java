package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.token.AccessTokenGenerateCommand;
import pl.edu.wit.application.command.token.AccessTokenRefreshCommand;
import pl.edu.wit.application.dto.AccessTokenDto;

public interface AccessTokenService {

    AccessTokenDto generate(AccessTokenGenerateCommand command);

    AccessTokenDto refresh(AccessTokenRefreshCommand command);

}
