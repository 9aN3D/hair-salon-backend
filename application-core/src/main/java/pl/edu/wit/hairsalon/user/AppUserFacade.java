package pl.edu.wit.hairsalon.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

import static java.util.Objects.requireNonNull;

class AppUserFacade implements UserFacade {

    private final UserPort userPort;
    private final UserCreator creator;
    private final UserUpdater updater;

    AppUserFacade(UserPort userPort, UserCreator creator, UserUpdater updater) {
        this.userPort = userPort;
        this.creator = creator;
        this.updater = updater;
    }

    @Override
    public String create(UserCreateCommand command) {
        requireNonNull(command, "User create command must not be null");
        var createdUser = creator.create(command);
        return createdUser.id();
    }

    @Override
    public void update(String userId, UserUpdateCommand command) {
        requireNonNull(userId, "User id must not be null");
        requireNonNull(command, "User update command must not be null");
        updater.update(userId, command);
    }

    @Override
    public UserDto findOne(String userId) {
        requireNonNull(userId, "User id must not be null");
        return userPort.findOneOrThrow(UserFindQuery.withId(userId));
    }

    @Override
    public Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "User find query must not be null");
        requireNonNull(pageable, "User find pageable must not be null");
        return userPort.findAll(findQuery, pageable);
    }
}
