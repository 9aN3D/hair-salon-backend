package pl.edu.wit.hairsalon.authDetails;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.withId;

@RequiredArgsConstructor
class AuthDetailsUpdater {

    private final AuthDetailsPort authDetailsPort;
    private final PasswordEncoderPort passwordEncoderPort;

    AuthDetailsDto update(String id, AuthDetailsUpdateCommand command) {
        var authDetailsDto = authDetailsPort.findOneOrThrow(withId(id));
        var updatedAuthDetails = buildAuthDetails(authDetailsDto, command)
                .validate()
                .toDto();
        return authDetailsPort.save(updatedAuthDetails);
    }

    private AuthDetails buildAuthDetails(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return AuthDetails.builder()
                .id(authDetailsDto.getId())
                .email(getEmail(authDetailsDto, command))
                .password(getPassword(authDetailsDto, command))
                .status(getAuthDetailsStatus(authDetailsDto, command))
                .role(getAuthDetailsRole(authDetailsDto))
                .build();
    }

    private Email getEmail(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return new Email(
                ofNullable(command.getEmail())
                        .orElseGet(authDetailsDto::getEmail)
        );
    }

    private Password getPassword(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return ofNullable(command.getPassword())
                .map(Password::new)
                .map(newPassword -> newPassword.encode(passwordEncoderPort))
                .orElseGet(() -> new Password(authDetailsDto.getEmail()));
    }

    private AuthDetailsStatus getAuthDetailsStatus(AuthDetailsDto authDetailsDto, AuthDetailsUpdateCommand command) {
        return AuthDetailsStatus.valueOf(
                ofNullable(command.getStatus())
                        .orElseGet(authDetailsDto::getStatus).name()
        );
    }

    private AuthDetailsRole getAuthDetailsRole(AuthDetailsDto authDetailsDto) {
        return AuthDetailsRole.valueOf(authDetailsDto.getRole());
    }

}
