package pl.edu.wit.hairsalon.token;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

public interface RefreshTokenService {

    void saveRefreshToken(String username, String refreshToken);

    boolean isValid(String username, String token);

    @Service
    class CaffeineCacheRefreshTokenService implements RefreshTokenService {

        private final Cache<String, String> cache;

        public CaffeineCacheRefreshTokenService(OAuthServerClientProperties oauthServerClientProperties) {
            this.cache = Caffeine.newBuilder()
                    .expireAfterWrite(oauthServerClientProperties.getRefreshTokenValidity())
                    .build();
        }

        @Override
        public void saveRefreshToken(String username, String refreshToken) {
            cache.put(username, refreshToken);
        }

        @Override
        public boolean isValid(String username, String token) {
            var cachedToken = cache.getIfPresent(username);
            return nonNull(cachedToken) && cachedToken.equals(token);
        }

    }

}
