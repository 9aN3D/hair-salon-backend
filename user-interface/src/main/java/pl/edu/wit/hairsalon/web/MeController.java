package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.authdetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.Identity;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/me", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class MeController {

    private final AuthDetailsFacade authDetailsFacade;

    @GetMapping
    @ResponseStatus(OK)
    AuthDetailsDto me(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return authDetailsFacade.findOneById(identity.getId());
    }

}
