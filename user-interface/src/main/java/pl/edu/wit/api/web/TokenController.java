package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.application.command.AccessTokenGenerateCommand;
import pl.edu.wit.application.command.AccessTokenRefreshCommand;
import pl.edu.wit.application.dto.AccessTokenDto;
import pl.edu.wit.application.port.primary.AccessTokenService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TokenController {

    private final AccessTokenService accessTokenService;

    @PostMapping
    @ResponseStatus(OK)
    public AccessTokenDto generate(@RequestBody AccessTokenGenerateCommand command) {
        return accessTokenService.generate(command);
    }

    @PostMapping(value = "/refresh")
    @ResponseStatus(OK)
    public AccessTokenDto refresh(@RequestBody AccessTokenRefreshCommand command) {
        return accessTokenService.refresh(command);
    }

}
