package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.auth_details.AuthDetailsFacade;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.common.Identity;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/me", produces = APPLICATION_JSON_VALUE)
public class MeController {

    private final AuthDetailsFacade authDetailsFacade;

    @GetMapping
    @ResponseStatus(OK)
    public AuthDetailsDto me(@AuthenticationPrincipal Identity identity) {
        return authDetailsFacade.getFindAuthDetailsUseCase().findOneByEmail(identity.getId());
    }

}
