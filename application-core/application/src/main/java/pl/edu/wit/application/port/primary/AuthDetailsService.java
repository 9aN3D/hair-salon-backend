package pl.edu.wit.application.port.primary;

import pl.edu.wit.domain.dto.AuthDetailsDto;

public interface AuthDetailsService {

    Boolean existByEmail(String email);

    AuthDetailsDto findOneByEmail(String email);

}
