package pl.edu.wit.hairsalon.token;

import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;

public interface AccessTokenPort {

    AccessTokenDto generate(String email, String password);

    AccessTokenDto refresh(String refreshToken);

}
