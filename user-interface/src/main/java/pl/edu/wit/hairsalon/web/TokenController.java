package pl.edu.wit.hairsalon.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.token.AccessTokenFacade;
import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
class TokenController {

    private final AccessTokenFacade accessTokenFacade;

    @PostMapping
    @ResponseStatus(OK)
    AccessTokenDto generate(@RequestBody AccessTokenGenerateCommand command) {
        return accessTokenFacade.generate(command);
    }

    @PostMapping(value = "/refresh")
    @ResponseStatus(OK)
    AccessTokenDto refresh(@RequestBody AccessTokenRefreshCommand command) {
        return accessTokenFacade.refresh(command);
    }

}
