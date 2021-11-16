package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

public interface AuthDetailsFacade {

    AuthDetailsDto create(AuthDetailsCreateCommand command);

    AuthDetailsDto update(String id, AuthDetailsUpdateCommand command);

    AuthDetailsDto findOneById(String id);

    void remove(String id);

}
