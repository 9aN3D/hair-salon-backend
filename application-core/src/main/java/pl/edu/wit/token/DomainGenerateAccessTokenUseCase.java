package pl.edu.wit.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.auth_details.domain.Email;
import pl.edu.wit.common.StringNotBlank;
import pl.edu.wit.token.port.primary.GenerateAccessTokenUseCase;
import pl.edu.wit.token.port.secondary.AccessTokenProvider;
import pl.edu.wit.token.shared.command.GenerateAccessTokenCommand;
import pl.edu.wit.token.shared.dto.AccessTokenDto;

@Slf4j
@RequiredArgsConstructor
class DomainGenerateAccessTokenUseCase implements GenerateAccessTokenUseCase {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public AccessTokenDto generate(GenerateAccessTokenCommand command) {
        log.trace("Generating access token {command: {}}", command);
        var validEmail = new Email(command.getEmail()).value();
        var validPassword = new StringNotBlank(command.getPassword()).validate();
        return accessTokenProvider.generate(validEmail, validPassword);
    }

}
