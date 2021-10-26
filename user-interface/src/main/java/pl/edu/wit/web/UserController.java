package pl.edu.wit.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.web.facade.UserFacade;
import pl.edu.wit.web.response.UserResponse;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.query.UserFindQuery;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
public class UserController {

    private final UserFacade userFacade;

    @GetMapping(value = "/admin/users/{authDetailsId}")
    @ResponseStatus(OK)
    public UserResponse findOne(@PathVariable String authDetailsId) {
        return userFacade.findOne(authDetailsId);
    }

    @GetMapping(value = "/admin/users")
    @ResponseStatus(OK)
    public PageSlice<UserResponse> findAll(@NotNull UserFindQuery findQuery, @NotNull Pageable pageable) {
        return userFacade.findAll(findQuery, pageable);
    }

}
