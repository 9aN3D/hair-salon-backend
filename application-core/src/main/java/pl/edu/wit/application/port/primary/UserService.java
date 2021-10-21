package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.UserDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.UserFindQuery;

public interface UserService {

    UserDto findOne(String authDetailsId);

    PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery);

}
