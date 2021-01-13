package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.domain.model.auth_details.AuthDetailsPassword;
import pl.edu.wit.domain.model.Email;

@RequiredArgsConstructor
public class AuthDetailsFactory {

    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthDetails createNewAuthDetails(CreateAuthDetailsCommand command) {
        return AuthDetails.builder()
                .id(idGenerator.generate())
                .password(new AuthDetailsPassword(passwordEncoder.encode(command.getPassword())))
                .email(new Email(command.getEmail()))
                .status(command.getStatus())
                .role(command.getRole())
                .build();
    }

}
