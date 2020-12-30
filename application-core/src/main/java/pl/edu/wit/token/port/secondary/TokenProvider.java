package pl.edu.wit.token.port.secondary;

import pl.edu.wit.token.shared.dto.TokenDto;

public interface TokenProvider {

    TokenDto generate(String email, String password);

    TokenDto refresh(String refreshToken);

}
