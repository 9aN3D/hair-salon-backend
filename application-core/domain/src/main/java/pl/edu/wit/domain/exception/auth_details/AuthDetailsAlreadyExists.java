package pl.edu.wit.domain.exception.auth_details;

import pl.edu.wit.domain.exception.DomainException;

public class AuthDetailsAlreadyExists extends DomainException {

    public AuthDetailsAlreadyExists(String message) {
        super(message);
    }

}
