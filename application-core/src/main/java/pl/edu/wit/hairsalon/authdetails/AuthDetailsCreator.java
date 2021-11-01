package pl.edu.wit.hairsalon.authdetails;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authdetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authdetails.exception.AuthDetailsCreateException;
import pl.edu.wit.hairsalon.authdetails.query.AuthDetailsFindQuery;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import static java.lang.String.format;
import static pl.edu.wit.hairsalon.authdetails.AuthDetailsStatus.ACTIVE;

@RequiredArgsConstructor
class AuthDetailsCreator {

    private final IdGenerator idGenerator;
    private final AuthDetailsPort authDetailsPort;
    private final PasswordEncoderPort passwordEncoderPort;

    AuthDetailsDto create(AuthDetailsCreateCommand command) {
        throwIfExistByCommandEmail(command.getEmail());
        var newAuthDetails = createNewAuthDetails(command)
                .validate()
                .encodePassword(passwordEncoderPort);
        return authDetailsPort.save(newAuthDetails.toDto());
    }

    private void throwIfExistByCommandEmail(String email) {
        if (existByEmail(email)) {
            throw new AuthDetailsCreateException(
                    format("Auth details already exists by email: %s", email));
        }
    }

    private Boolean existByEmail(String email) {
        return authDetailsPort.findOne(AuthDetailsFindQuery.ofEmail(email)).isPresent();
    }

    private AuthDetails createNewAuthDetails(AuthDetailsCreateCommand command) {
        return AuthDetails.builder()
                .id(idGenerator.generate())
                .password(new Password(command.getPassword()))
                .email(new Email(command.getEmail()))
                .status(ACTIVE)
                .role(AuthDetailsRole.valueOf(command.getRole()))
                .build();
    }

}
