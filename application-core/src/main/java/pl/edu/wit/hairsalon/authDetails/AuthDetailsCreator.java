package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.exception.AuthDetailsCreateException;
import pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import static java.lang.String.format;
import static pl.edu.wit.hairsalon.authDetails.AuthDetailsStatus.ACTIVE;

class AuthDetailsCreator {

    private final IdGenerator idGenerator;
    private final AuthDetailsPort authDetailsPort;
    private final PasswordEncoderPort passwordEncoderPort;

    AuthDetailsCreator(IdGenerator idGenerator, AuthDetailsPort authDetailsPort, PasswordEncoderPort passwordEncoderPort) {
        this.idGenerator = idGenerator;
        this.authDetailsPort = authDetailsPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    AuthDetailsDto create(AuthDetailsCreateCommand command) {
        throwIfExistByCommandEmail(command.email());
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
                .password(new Password(command.password()))
                .email(new Email(command.email()))
                .status(ACTIVE)
                .role(AuthDetailsRole.valueOf(command.role()))
                .build();
    }

}
