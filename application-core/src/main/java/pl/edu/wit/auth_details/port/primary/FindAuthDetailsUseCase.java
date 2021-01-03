package pl.edu.wit.auth_details.port.primary;

import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

import java.util.Optional;

public interface FindAuthDetailsUseCase {

    AuthDetailsDto findOneByEmail(String email);

}
