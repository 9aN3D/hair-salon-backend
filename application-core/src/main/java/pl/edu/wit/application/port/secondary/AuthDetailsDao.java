package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.query.AuthDetailsFindQuery;

import java.util.Optional;

public interface AuthDetailsDao {

    Optional<AuthDetailsDto> findOne(AuthDetailsFindQuery query);

    AuthDetailsDto save(AuthDetailsDto authDetails);

}
