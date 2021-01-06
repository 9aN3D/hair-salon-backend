package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.application.command.GenerateAccessTokenCommand;
import pl.edu.wit.application.command.RefreshAccessTokenCommand;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.domain.dto.AccessTokenDto;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TokenController {

    private final AccessTokenService accessTokenService;

    @PostMapping
    @ResponseStatus(OK)
    public AccessTokenDto generate(@RequestBody GenerateAccessTokenCommand command) {
        return accessTokenService.generate(command);
    }

    @PostMapping(value = "/refresh")
    @ResponseStatus(OK)
    public AccessTokenDto refresh(@RequestBody RefreshAccessTokenCommand command) {
        return accessTokenService.refresh(command);
    }

}
