package pl.edu.wit.application.port.secondary;

import pl.edu.wit.domain.dto.AccessTokenDto;

public interface AccessTokenProvider {

    AccessTokenDto generate(String email, String password);

    AccessTokenDto refresh(String refreshToken);

}
