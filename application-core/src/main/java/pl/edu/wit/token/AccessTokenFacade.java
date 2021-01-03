package pl.edu.wit.token;

import lombok.Getter;
import pl.edu.wit.token.port.primary.GenerateAccessTokenUseCase;
import pl.edu.wit.token.port.primary.RefreshAccessTokenUseCase;
import pl.edu.wit.token.port.secondary.AccessTokenProvider;

@Getter
public class AccessTokenFacade {

    private final GenerateAccessTokenUseCase generateAccessTokenUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

    public AccessTokenFacade(AccessTokenProvider accessTokenProvider) {
        generateAccessTokenUseCase = new DomainGenerateAccessTokenUseCase(accessTokenProvider);
        refreshAccessTokenUseCase = new DomainRefreshAccessTokenUseCase(accessTokenProvider);
    }

}
