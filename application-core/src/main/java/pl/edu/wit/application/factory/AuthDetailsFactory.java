package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.AuthDetailsCreateCommand;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.EncodedPassword;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PasswordEncoder;

@RequiredArgsConstructor
public class AuthDetailsFactory {

    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthDetails createNewAuthDetails(AuthDetailsCreateCommand command) {
        return AuthDetails.builder()
                .id(idGenerator.generate())
                .password(
                        new EncodedPassword(
                                new AuthDetailsPassword(command.getPassword()),
                                passwordEncoder))
                .email(new Email(command.getEmail()))
                .status(command.getStatus())
                .role(command.getRole())
                .build();
    }

}
