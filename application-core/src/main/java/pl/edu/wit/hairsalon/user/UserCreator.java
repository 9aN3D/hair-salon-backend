package pl.edu.wit.hairsalon.user;

import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;

import static java.time.LocalDateTime.now;

class UserCreator {

    private final UserPort userPort;
    private final AuthDetailsFacade authDetailsFacade;

    UserCreator(UserPort userPort, AuthDetailsFacade authDetailsFacade) {
        this.userPort = userPort;
        this.authDetailsFacade = authDetailsFacade;
    }

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
            authDetailsFacade.remove(savedAuthDetails.id());
            throw ex;
        }
    }

    private User createNewUser(UserCreateCommand command, AuthDetailsDto savedAuthDetails) {
        return User.builder()
                .id(savedAuthDetails.id())
                .fullName(getFullName(command))
                .contact(getContact(command))
                .registrationDateTime(now())
                .build();
    }

    private FullName getFullName(UserCreateCommand command) {
        return new FullName(command.name(), command.surname());
    }

    private UserContact getContact(UserCreateCommand command) {
        return new UserContact(command.email());
    }

}
