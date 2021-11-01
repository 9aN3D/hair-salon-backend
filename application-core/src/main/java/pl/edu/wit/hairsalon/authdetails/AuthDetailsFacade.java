package pl.edu.wit.hairsalon.authdetails;

import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;

public interface AuthDetailsFacade {

    AuthDetailsDto create(AuthDetailsCreateCommand command);

    AuthDetailsDto update(String id, AuthDetailsUpdateCommand command);

    AuthDetailsDto findOneById(String id);

    void remove(String id);

}
