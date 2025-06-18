package pl.edu.wit.hairsalon.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

class LoggableUserFacade implements UserFacade {

    private final Logger log;
    private final UserFacade delegate;

    LoggableUserFacade(UserFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableUserFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String create(UserCreateCommand command) {
        log.trace("Creating user, command: {}", command);
        var id = delegate.create(command);
        log.info("Created user, command: {}, id: {}", command, id);
        return id;
    }

    @Override
    public void update(String userId, UserUpdateCommand command) {
        log.trace("Updating user, userId: {}, command: {}", userId, command);
        delegate.update(userId, command);
        log.info("Updated user, userId: {}, command: {}", userId, command);
    }

    @Override
    public UserDto findOne(String userId) {
        log.trace("Getting user, userId: {}", userId);
        var dto = delegate.findOne(userId);
        log.info("Got user, dto: {}", dto);
        return dto;
    }

    @Override
    public Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable) {
        log.trace("Searching users, findQuery: {}, pageable: {}", findQuery, pageable);
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched users, numberOfElements: {}", page.getNumberOfElements());
        return page;
    }

}
