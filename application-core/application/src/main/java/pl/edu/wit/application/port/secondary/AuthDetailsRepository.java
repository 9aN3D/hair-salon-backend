package pl.edu.wit.application.port.secondary;

import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.model.auth_details.AuthDetails;

import java.util.Optional;

public interface AuthDetailsRepository {

    String save(AuthDetails authDetails);

    Optional<AuthDetailsDto> findByEmail(String email);

}
