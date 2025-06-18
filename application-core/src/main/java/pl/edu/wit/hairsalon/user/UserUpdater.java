package pl.edu.wit.hairsalon.user;

import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserDto;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.user.query.UserFindQuery.withId;

class UserUpdater {

    private final UserPort userPort;
    private final AuthDetailsFacade authDetailsFacade;

    UserUpdater(UserPort userPort, AuthDetailsFacade authDetailsFacade) {
        this.userPort = userPort;
        this.authDetailsFacade = authDetailsFacade;
    }

    UserDto update(String userId, UserUpdateCommand command) {
        var userDto = userPort.findOneOrThrow(withId(userId));
        var previousAuthDetails = authDetailsFacade.findOneById(userId);
        var updatedAuthDetails = authDetailsFacade.update(userId, command.toAuthDetailsUpdateCommand());
        return tryUpdateUser(previousAuthDetails, command, userDto, updatedAuthDetails);
    }

    private UserDto tryUpdateUser(AuthDetailsDto previousAuthDetails,
                                  UserUpdateCommand command,
                                  UserDto userDto,
                                  AuthDetailsDto updatedAuthDetails) {
        try {
            var user = buildUser(userDto, command, updatedAuthDetails)
                    .validate();
            return userPort.save(user.toDto());
        } catch (Exception ex) {
            if (command.isNotBlankEmail() || command.isNotBlankPassword()) {
                authDetailsFacade.update(previousAuthDetails.id(), AuthDetailsUpdateCommand.builder()
                        .email(command.email())
                        .password(command.password())
                        .build());
            }
            throw ex;
        }
    }

    public User buildUser(UserDto user, UserUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return User.builder()
                .id(user.id())
                .fullName(getFullName(user.fullName(), command))
                .contact(getContact(user.contact(), command, authDetailsDto))
                .registrationDateTime(user.registrationDateTime())
                .build();
    }

    private FullName getFullName(FullNameDto fullName, UserUpdateCommand command) {
        return new FullName(
                ofNullable(command.name()).orElseGet(fullName::name),
                ofNullable(command.surname()).orElseGet(fullName::surname)
        );
    }

    private UserContact getContact(UserContactDto contact, UserUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return new UserContact(
                nonNull(command.email())
                        ? authDetailsDto.email()
                        : contact.email()
        );
    }

}
