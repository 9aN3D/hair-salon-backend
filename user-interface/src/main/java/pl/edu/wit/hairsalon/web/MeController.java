package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/me", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class MeController {

    private final AuthDetailsFacade authDetailsFacade;

    MeController(AuthDetailsFacade authDetailsFacade) {
        this.authDetailsFacade = authDetailsFacade;
    }

    @GetMapping
    @ResponseStatus(OK)
    AuthDetailsDto me(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return authDetailsFacade.findOneById(identity.id());
    }

}
