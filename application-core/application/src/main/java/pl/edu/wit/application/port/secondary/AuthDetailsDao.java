package pl.edu.wit.application.port.secondary;

import pl.edu.wit.domain.model.auth_details.AuthDetails;

import java.util.Optional;

public interface AuthDetailsDao {

    Optional<AuthDetails> findOne(String email);

}
