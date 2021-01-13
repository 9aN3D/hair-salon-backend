package pl.edu.wit.spring.adapter.service;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.port.primary.AccessTokenService;
import pl.edu.wit.application.port.secondary.AccessTokenProvider;
import pl.edu.wit.application.service.AppAccessTokenService;

@Service
public class ProxyAppAccessTokenService implements AccessTokenService {

    @Delegate
    private final AccessTokenService accessTokenService;

    @Autowired
    public ProxyAppAccessTokenService(AccessTokenProvider accessTokenProvider) {
        accessTokenService = new AppAccessTokenService(accessTokenProvider);
    }

}
