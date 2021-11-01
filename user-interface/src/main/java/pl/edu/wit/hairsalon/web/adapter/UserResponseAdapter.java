package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.user.UserFacade;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;
import pl.edu.wit.hairsalon.web.response.UserResponse;

@Service
@RequiredArgsConstructor
public class UserResponseAdapter {

    private final UserFacade userFacade;

    public UserResponse findOne(String authDetailsId) {
        return UserResponse.of(userFacade.findOne(authDetailsId));
    }

    public Page<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable) {
        return userFacade.findAll(findQuery, pageable)
                .map(UserResponse::of);
    }

}
