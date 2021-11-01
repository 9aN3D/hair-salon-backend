package pl.edu.wit.hairsalon.token;

import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

public interface AccessTokenFacade {

    AccessTokenDto generate(AccessTokenGenerateCommand command);

    AccessTokenDto refresh(AccessTokenRefreshCommand command);

}
