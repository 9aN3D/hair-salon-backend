package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.domain.model.user.User;
import pl.edu.wit.application.dto.UserDto;
import pl.edu.wit.application.exception.user.UserNotFoundException;
import pl.edu.wit.application.port.primary.UserService;
import pl.edu.wit.application.port.secondary.UserDao;
import pl.edu.wit.application.query.UserFindQuery;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppUserService implements UserService {

    private final UserDao userDao;

    @Override
    public UserDto getOne(String authDetailsId) {
        log.trace("Getting user {authDetailsId: {}}", authDetailsId);
        var userDto = userDao.findOne(UserFindQuery.ofAuthDetailsId(authDetailsId))
                .map(User::toDto)
                .orElseThrow(() -> new UserNotFoundException(
                        format("User not found by authDetailsId: %s", authDetailsId))
                );
        log.info("Got user {dto: {}}", userDto);
        return userDto;
    }

}
