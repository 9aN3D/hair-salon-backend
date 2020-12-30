package pl.edu.wit.token.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.auth_details.domain.Email;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.token.port.primary.GenerateAccessToken;
import pl.edu.wit.token.port.secondary.TokenProvider;
import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.dto.TokenDto;

@Slf4j
@RequiredArgsConstructor
public class GenerateAccessTokenUseCase implements GenerateAccessToken {

    private final TokenProvider tokenProvider;

    @Override
    public TokenDto generate(GenerateAccessTokenCommand command) {
        log.trace("Generating access token {command: {}}", command);
        var validEmail = new Email(command.getEmail()).value();
        var validPassword = new StringNotBlank(command.getPassword()).validate();
        return tokenProvider.generate(validEmail, validPassword);
    }

}
