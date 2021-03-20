package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.domain.model.user.User;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.UserFindQuery;

import java.util.Optional;

public interface UserDao {

    Optional<User> findOne(UserFindQuery query);

    PageSlice<User> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery);

}
