package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.query.AuthDetailsFindQuery;

import java.util.Optional;

public interface AuthDetailsDao {

    Optional<AuthDetails> findOne(AuthDetailsFindQuery query);

}
