package pl.edu.wit.hairsalon.authdetails;

import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authdetails.query.AuthDetailsFindQuery;

import java.util.Optional;

public interface AuthDetailsPort {

    AuthDetailsDto save(AuthDetailsDto authDetails);

    Optional<AuthDetailsDto> findOne(AuthDetailsFindQuery query);

    AuthDetailsDto findOneOrThrow(AuthDetailsFindQuery id);

    void delete(String id);

}
