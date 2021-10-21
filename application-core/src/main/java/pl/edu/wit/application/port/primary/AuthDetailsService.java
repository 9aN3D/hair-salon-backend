package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.dto.AuthDetailsDto;

public interface AuthDetailsService {

    AuthDetailsDto save(String email, String password);

    AuthDetailsDto findOneById(String id);

}
