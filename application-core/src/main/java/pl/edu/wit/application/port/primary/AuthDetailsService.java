package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.dto.AuthDetailsDto;

public interface AuthDetailsService {

    Boolean existByEmail(String email);

    AuthDetailsDto findOneById(String id);

}
