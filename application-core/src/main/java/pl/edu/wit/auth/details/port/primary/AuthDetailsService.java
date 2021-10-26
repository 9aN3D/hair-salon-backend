package pl.edu.wit.auth.details.port.primary;

import pl.edu.wit.auth.details.dto.AuthDetailsDto;

public interface AuthDetailsService {

    AuthDetailsDto save(String email, String password);

    AuthDetailsDto findOneById(String id);

}
