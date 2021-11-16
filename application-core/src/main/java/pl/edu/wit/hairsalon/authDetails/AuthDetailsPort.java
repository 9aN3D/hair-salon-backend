package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery;

import java.util.Optional;

public interface AuthDetailsPort {

    AuthDetailsDto save(AuthDetailsDto authDetails);

    Optional<AuthDetailsDto> findOne(AuthDetailsFindQuery query);

    AuthDetailsDto findOneOrThrow(AuthDetailsFindQuery id);

    void delete(String id);

}
