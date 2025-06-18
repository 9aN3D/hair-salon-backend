package pl.edu.wit.hairsalon.token;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.StringJoiner;

@Valid
@ConfigurationProperties(prefix = "pl.edu.wit.oauth.client")
public class SpringOAuthServerClientProperties implements OAuthServerClientProperties {

    @NotBlank
    private String clientPassword;

    @NotNull
    private Duration accessTokenValidity;

    @NotNull
    private Duration refreshTokenValidity;

    @Override
    public byte[] convertClientPasswordToBytes() {
        return clientPassword.getBytes(StandardCharsets.UTF_8);
    }

    public SpringOAuthServerClientProperties() {
    }

    public SpringOAuthServerClientProperties(String clientPassword, Duration accessTokenValidity, Duration refreshTokenValidity) {
        this.clientPassword = clientPassword;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Override
    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    @Override
    public Duration getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Duration accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    @Override
    public Duration getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Duration refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SpringOAuthServerClientProperties.class.getSimpleName() + "[", "]")
                .add("accessTokenValidity=" + accessTokenValidity)
                .add("refreshTokenValidity=" + refreshTokenValidity)
                .toString();
    }

}
