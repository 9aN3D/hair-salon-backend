package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;
import pl.edu.wit.hairsalon.web.adapter.UserResponseAdapter;
import pl.edu.wit.hairsalon.web.response.UserResponse;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class UserController {

    private final UserResponseAdapter userResponseAdapter;

    @GetMapping(value = "/admin/users/{authDetailsId}")
    @ResponseStatus(OK)
    UserResponse findOne(@PathVariable String authDetailsId) {
        return userResponseAdapter.findOne(authDetailsId);
    }

    @GetMapping(value = "/admin/users")
    @ResponseStatus(OK)
    Page<UserResponse> findAll(@NotNull UserFindQuery findQuery, @NotNull Pageable pageable) {
        return userResponseAdapter.findAll(findQuery, pageable);
    }

}
