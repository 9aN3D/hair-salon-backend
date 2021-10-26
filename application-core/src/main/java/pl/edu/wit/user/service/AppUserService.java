package pl.edu.wit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.dto.UserDto;
import pl.edu.wit.user.exception.UserNotFoundException;
import pl.edu.wit.user.port.primary.UserService;
import pl.edu.wit.user.port.secondary.UserDao;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.user.query.UserFindQuery;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AppUserService implements UserService {

    private final UserDao userDao;

    @Override
    public UserDto findOne(String authDetailsId) {
        log.trace("Getting user {authDetailsId: {}}", authDetailsId);
        var userDto = userDao.findOne(UserFindQuery.ofAuthDetailsId(authDetailsId))
                .orElseThrow(() -> new UserNotFoundException(
                        format("User not found by authDetailsId: %s", authDetailsId))
                );
        log.info("Got user {dto: {}}", userDto);
        return userDto;
    }

    @Override
    public PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery) {
        log.trace("Searching users {findQuery: {}, paramsQuery: {}}", findQuery, paramsQuery);
        var page = userDao.findAll(findQuery, paramsQuery);
        log.info("Searched users {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

}
