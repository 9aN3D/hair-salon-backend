package pl.edu.wit.user.port.secondary;

import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.dto.UserDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.user.query.UserFindQuery;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> findOne(UserFindQuery query);

    PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery);

}
