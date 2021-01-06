package pl.edu.wit.spring.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.command.GenerateAccessTokenCommand;
import pl.edu.wit.application.command.RefreshAccessTokenCommand;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.application.port.secondary.AccessTokenProvider;
import pl.edu.wit.application.service.AppAccessTokenService;
import pl.edu.wit.domain.dto.AccessTokenDto;

@Service
public class ProxyAppAccessTokenService implements AccessTokenService {

    private final AccessTokenService accessTokenService;

    @Autowired
    public ProxyAppAccessTokenService(AccessTokenProvider accessTokenProvider) {
        accessTokenService = new AppAccessTokenService(accessTokenProvider);
    }

    @Override
    public AccessTokenDto generate(GenerateAccessTokenCommand command) {
        return accessTokenService.generate(command);
    }

    @Override
    public AccessTokenDto refresh(RefreshAccessTokenCommand command) {
        return accessTokenService.refresh(command);
    }

}
