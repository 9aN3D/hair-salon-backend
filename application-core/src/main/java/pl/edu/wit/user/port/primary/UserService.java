package pl.edu.wit.user.port.primary;

import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.dto.UserDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.user.query.UserFindQuery;

public interface UserService {

    UserDto findOne(String authDetailsId);

    PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery);

}
