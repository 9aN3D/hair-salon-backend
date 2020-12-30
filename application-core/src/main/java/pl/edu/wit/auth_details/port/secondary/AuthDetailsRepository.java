package pl.edu.wit.auth_details.port.secondary;

import pl.edu.wit.auth_details.domain.AuthDetails;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

import java.util.Optional;

public interface AuthDetailsRepository {

    String save(AuthDetails authDetails);

    Optional<AuthDetailsDto> findByEmail(String email);

}
