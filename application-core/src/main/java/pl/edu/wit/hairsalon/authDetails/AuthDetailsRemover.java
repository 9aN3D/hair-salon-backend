package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;

import static pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery.withId;

class AuthDetailsRemover {

    private final AuthDetailsPort authDetailsPort;

    AuthDetailsRemover(AuthDetailsPort authDetailsPort) {
        this.authDetailsPort = authDetailsPort;
    }

    void remove(String id) {
        authDetailsPort.findOne(withId(id))
                .map(AuthDetailsDto::id)
                .ifPresent(authDetailsPort::delete);
    }

}
