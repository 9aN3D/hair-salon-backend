package pl.edu.wit.hairsalon.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppUserFacade implements UserFacade {

    private final UserPort userPort;
    private final UserCreator creator;
    private final UserUpdater updater;

    @Override
    public String create(UserCreateCommand command) {
        log.trace("Creating user {command: {}}", command);
        requireNonNull(command, "User create command must not be null");
        var createdUser = creator.create(command);
        log.info("Created user {userDto: {}}", createdUser);
        return createdUser.id();
    }

    @Override
    public void update(String userId, UserUpdateCommand command) {
        log.trace("Updating user {userId: {}, command: {}}", userId, command);
        requireNonNull(userId, "User id must not be null");
        requireNonNull(command, "User update command must not be null");
        var updatedUser = updater.update(userId, command);
        log.info("Updated user {userDto: {}}", updatedUser);
    }

    @Override
    public UserDto findOne(String userId) {
        log.trace("Getting user {userId: {}}", userId);
        requireNonNull(userId, "User id must not be null");
        var userDto = userPort.findOneOrThrow(UserFindQuery.withId(userId));
        log.info("Got user {dto: {}}", userDto);
        return userDto;
    }

    @Override
    public Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable) {
        log.trace("Searching users {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "User find query must not be null");
        requireNonNull(pageable, "User find pageable must not be null");
        var page = userPort.findAll(findQuery, pageable);
        log.info("Searched users {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
