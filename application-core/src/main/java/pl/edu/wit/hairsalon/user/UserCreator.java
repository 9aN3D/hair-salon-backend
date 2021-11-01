package pl.edu.wit.hairsalon.user;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authdetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
class UserCreator {

    private final UserPort userPort;
    private final AuthDetailsFacade authDetailsFacade;

    UserDto create(UserCreateCommand command) {
        var savedAuthDetails = saveAuthDetails(command);
        return trySaveNewUser(command, savedAuthDetails);
    }

    private AuthDetailsDto saveAuthDetails(UserCreateCommand command) {
        return authDetailsFacade.create(command.toAuthDetailsCreateCommand());
    }

    private UserDto trySaveNewUser(UserCreateCommand command, AuthDetailsDto savedAuthDetails) {
        try {
            var user = createNewUser(command, savedAuthDetails)
                    .validate();
            return userPort.save(user.toDto());
        } catch (Exception ex) {
            authDetailsFacade.remove(savedAuthDetails.getId());
            throw ex;
        }
    }

    private User createNewUser(UserCreateCommand command, AuthDetailsDto savedAuthDetails) {
        return User.builder()
                .id(savedAuthDetails.getId())
                .fullName(getFullName(command))
                .contact(getContact(command))
                .registrationDateTime(now())
                .build();
    }

    private UserFullName getFullName(UserCreateCommand command) {
        return new UserFullName(command.getName(), command.getSurname());
    }

    private UserContact getContact(UserCreateCommand command) {
        return new UserContact(command.getEmail());
    }

}
