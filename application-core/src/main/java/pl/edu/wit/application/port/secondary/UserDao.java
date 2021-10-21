package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.UserDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.UserFindQuery;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> findOne(UserFindQuery query);

    PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery);

}
