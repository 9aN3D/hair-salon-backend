package pl.edu.wit.domain.exception.auth_details;

import pl.edu.wit.domain.exception.DomainException;

public class AuthDetailsNotFoundException extends DomainException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
