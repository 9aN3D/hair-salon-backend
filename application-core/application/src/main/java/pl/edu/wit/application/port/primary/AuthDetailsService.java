package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.domain.dto.AuthDetailsDto;

public interface AuthDetailsService {

    String create(CreateAuthDetailsCommand command);

    AuthDetailsDto findOneByEmail(String email);

}
