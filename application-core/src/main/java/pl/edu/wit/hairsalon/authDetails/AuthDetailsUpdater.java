package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.withId;

class AuthDetailsUpdater {

    private final AuthDetailsPort authDetailsPort;
    private final PasswordEncoderPort passwordEncoderPort;

    AuthDetailsUpdater(AuthDetailsPort authDetailsPort, PasswordEncoderPort passwordEncoderPort) {
        this.authDetailsPort = authDetailsPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    AuthDetailsDto update(String id, AuthDetailsUpdateCommand command) {
        var authDetailsDto = authDetailsPort.findOneOrThrow(withId(id));
        var updatedAuthDetails = buildAuthDetails(authDetailsDto, command)
                .validate()
                .toDto();
        return authDetailsPort.save(updatedAuthDetails);
    }

    private AuthDetails buildAuthDetails(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return AuthDetails.builder()
                .id(authDetailsDto.id())
                .email(getEmail(authDetailsDto, command))
                .password(getPassword(authDetailsDto, command))
                .status(getAuthDetailsStatus(authDetailsDto, command))
                .role(getAuthDetailsRole(authDetailsDto))
                .build();
    }

    private Email getEmail(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return new Email(
                ofNullable(command.email())
                        .orElseGet(authDetailsDto::email)
        );
    }

    private Password getPassword(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return ofNullable(command.password())
                .map(Password::new)
                .map(newPassword -> newPassword.encode(passwordEncoderPort))
                .orElseGet(() -> new Password(authDetailsDto.password()));
    }

    private AuthDetailsStatus getAuthDetailsStatus(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return AuthDetailsStatus.valueOf(
                ofNullable(command.status())
                        .orElseGet(authDetailsDto::status).name()
        );
    }

    private AuthDetailsRole getAuthDetailsRole(AuthDetailsDto authDetailsDto) {
        return AuthDetailsRole.valueOf(authDetailsDto.role());
    }

}
