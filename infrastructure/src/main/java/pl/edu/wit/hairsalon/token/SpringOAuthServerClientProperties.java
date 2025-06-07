package pl.edu.wit.hairsalon.token;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Data
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

}
