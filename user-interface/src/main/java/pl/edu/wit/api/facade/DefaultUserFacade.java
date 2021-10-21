package pl.edu.wit.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.api.mapper.PageableParamsMapper;
import pl.edu.wit.api.response.UserResponse;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.port.primary.UserService;
import pl.edu.wit.application.query.UserFindQuery;

@Service
@RequiredArgsConstructor
public class DefaultUserFacade implements UserFacade {

    private final UserService userService;
    private final PageableParamsMapper pageableParamsMapper;

    @Override
    public UserResponse findOne(String authDetailsId) {
        return UserResponse.of(userService.findOne(authDetailsId));
    }

    @Override
    public PageSlice<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable) {
        return userService.findAll(findQuery, pageableParamsMapper.toPageableParamsQuery(pageable))
                .map(UserResponse::of);
    }

}
