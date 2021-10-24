package pl.edu.wit.api.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.dto.Identity;
import pl.edu.wit.application.port.primary.AuthDetailsService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/me", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
public class MeController {

    private final AuthDetailsService authDetailsService;

    @GetMapping
    @ResponseStatus(OK)
    public AuthDetailsDto me(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return authDetailsService.findOneById(identity.getId());
    }

}
