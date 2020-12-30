package pl.edu.wit.auth_details.port.primary;

import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

import java.util.Optional;

public interface FindAuthDetails {

    Optional<AuthDetailsDto> findOneByEmail(String email);

}
