package pl.edu.wit.hairsalon.authdetails;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;

import static pl.edu.wit.hairsalon.authdetails.query.AuthDetailsFindQuery.withId;

@RequiredArgsConstructor
class AuthDetailsRemover {

    private final AuthDetailsPort authDetailsPort;

    void remove(String id) {
        authDetailsPort.findOne(withId(id))
                .map(AuthDetailsDto::getId)
                .ifPresent(authDetailsPort::delete);
    }

}
