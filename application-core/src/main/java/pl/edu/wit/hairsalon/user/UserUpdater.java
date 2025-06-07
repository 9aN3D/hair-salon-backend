package pl.edu.wit.hairsalon.user;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.dto.UserFullNameDto;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.user.query.UserFindQuery.withId;

@RequiredArgsConstructor
class UserUpdater {

    private final UserPort userPort;
    private final AuthDetailsFacade authDetailsFacade;

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
                        .email(command.getEmail())
                        .password(command.getPassword())
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

    private UserFullName getFullName(UserFullNameDto fullName, UserUpdateCommand command) {
        return UserFullName.builder()
                .name(ofNullable(command.getName())
                        .orElseGet(fullName::getName))
                .surname(ofNullable(command.getSurname())
                        .orElseGet(fullName::getSurname))
                .build();
    }

    private UserContact getContact(UserContactDto contact, UserUpdateCommand command, AuthDetailsDto authDetailsDto) {
        return UserContact.builder()
                .email(nonNull(command.getEmail())
                        ? authDetailsDto.email()
                        : contact.getEmail())
                .build();
    }

}
