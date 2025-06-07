package pl.edu.wit.hairsalon.token;

import java.time.Duration;

public interface OAuthServerClientProperties {

    String getClientPassword();

    Duration getAccessTokenValidity();

    Duration getRefreshTokenValidity();

    byte[] convertClientPasswordToBytes();

}
