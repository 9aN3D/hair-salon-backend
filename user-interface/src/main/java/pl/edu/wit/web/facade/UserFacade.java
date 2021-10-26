package pl.edu.wit.web.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.web.response.UserResponse;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.query.UserFindQuery;

public interface UserFacade {

    UserResponse findOne(String authDetailsId);

    PageSlice<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable);

}
