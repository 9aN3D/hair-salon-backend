package pl.edu.wit.hairsalon.authDetails;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.withId;

@RequiredArgsConstructor
class AuthDetailsRemover {

    private final AuthDetailsPort authDetailsPort;

    void remove(String id) {
        authDetailsPort.findOne(withId(id))
                .map(AuthDetailsDto::id)
                .ifPresent(authDetailsPort::delete);
    }

}
