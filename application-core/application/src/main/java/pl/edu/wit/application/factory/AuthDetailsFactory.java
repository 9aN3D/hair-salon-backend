package pl.edu.wit.application.factory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.command.UpdateMemberCommand;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.domain.model.Email;
import pl.edu.wit.domain.model.auth_details.AuthDetails;
import pl.edu.wit.domain.model.auth_details.AuthDetailsPassword;

import static java.util.Optional.ofNullable;

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

    public AuthDetails updateAuthDetails(UpdateMemberCommand command, AuthDetails oldAuthDetails) {
        return AuthDetails.builder()
                .id(oldAuthDetails.getId())
                .password(ofNullable(command.getPassword())
                        .map(passwordEncoder::encode)
                        .map(AuthDetailsPassword::new).orElseGet(oldAuthDetails::getPassword))
                .email(ofNullable(command.getEmail())
                        .map(Email::new)
                        .orElseGet(oldAuthDetails::getEmail))
                .status(oldAuthDetails.getStatus())
                .role(oldAuthDetails.getRole())
                .build();
    }

}
