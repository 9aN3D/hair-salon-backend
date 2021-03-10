package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.response.UserResponse;
import pl.edu.wit.application.port.primary.UserService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/admin/users/{authDetailsId}")
    @ResponseStatus(OK)
    public UserResponse get(@PathVariable String authDetailsId) {
        return UserResponse.of(userService.getOne(authDetailsId));
    }

}
