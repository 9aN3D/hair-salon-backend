package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.token.AccessTokenFacade;
import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.AccessTokenDto;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TokenController {

    private final AccessTokenFacade accessTokenFacade;

    @PostMapping
    public AccessTokenDto generate(@RequestBody GenerateAccessTokenCommand command) {
        return accessTokenFacade.getGenerateAccessTokenUseCase().generate(command);
    }

    @PostMapping(value = "/refresh")
    public AccessTokenDto refresh(@RequestBody RefreshAccessTokenCommand command) {
        return accessTokenFacade.getRefreshAccessTokenUseCase().refresh(command);
    }

}
