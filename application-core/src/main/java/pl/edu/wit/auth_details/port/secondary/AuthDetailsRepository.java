package pl.edu.wit.auth_details.port.secondary;

import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;

public interface AuthDetailsRepository {

    String save(AuthDetailsDto dto);

}
