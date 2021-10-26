package pl.edu.wit.token.port.secondary;

import pl.edu.wit.token.dto.AccessTokenDto;

public interface AccessTokenProvider {

    AccessTokenDto generate(String email, String password);

    AccessTokenDto refresh(String refreshToken);

}
