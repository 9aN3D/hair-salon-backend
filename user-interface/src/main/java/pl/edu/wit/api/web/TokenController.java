package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.token.port.primary.GenerateAccessToken;
import pl.edu.wit.token.port.primary.RefreshAccessToken;
import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.command.RefreshAccessTokenCommand;
import pl.edu.wit.token.shared.dto.TokenDto;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TokenController {

    private final GenerateAccessToken generateAccessTokenUseCase;
    private final RefreshAccessToken refreshAccessTokenUseCase;

    @PostMapping
    public TokenDto generate(@RequestBody GenerateAccessTokenCommand command) {
        return generateAccessTokenUseCase.generate(command);
    }

    @PostMapping(value = "/refresh")
    public TokenDto refresh(@RequestBody RefreshAccessTokenCommand command) {
        return refreshAccessTokenUseCase.refresh(command);
    }

}
