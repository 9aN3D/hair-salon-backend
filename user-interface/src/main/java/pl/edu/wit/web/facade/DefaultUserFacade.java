package pl.edu.wit.web.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.web.mapper.PageableParamsMapper;
import pl.edu.wit.web.response.UserResponse;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.port.primary.UserService;
import pl.edu.wit.user.query.UserFindQuery;

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
