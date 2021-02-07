package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.AccessTokenGenerateCommand;
import pl.edu.wit.application.command.AccessTokenRefreshCommand;
import pl.edu.wit.application.dto.AccessTokenDto;

public interface AccessTokenService {

    AccessTokenDto generate(AccessTokenGenerateCommand command);

    AccessTokenDto refresh(AccessTokenRefreshCommand command);

}
