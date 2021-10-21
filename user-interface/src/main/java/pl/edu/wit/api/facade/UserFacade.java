package pl.edu.wit.api.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.api.response.UserResponse;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.UserFindQuery;

public interface UserFacade {

    UserResponse findOne(String authDetailsId);

    PageSlice<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable);

}
