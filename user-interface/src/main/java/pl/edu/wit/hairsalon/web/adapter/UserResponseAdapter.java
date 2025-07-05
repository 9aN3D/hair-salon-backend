package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.user.UserFacade;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.UserResponse;

@Service
public class UserResponseAdapter {

    private final UserFacade userFacade;

    public UserResponseAdapter(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserResponse findOne(String authDetailsId) {
        return UserResponse.of(userFacade.findOne(authDetailsId));
    }

    public PagedResponse<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                userFacade.findAll(findQuery, pageable)
                        .map(UserResponse::of)
        );
    }

}
