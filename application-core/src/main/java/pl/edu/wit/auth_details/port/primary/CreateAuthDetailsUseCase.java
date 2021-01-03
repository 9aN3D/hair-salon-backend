package pl.edu.wit.auth_details.port.primary;

import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;

public interface CreateAuthDetailsUseCase {

    String create(CreateAuthDetailsCommand command);

}
