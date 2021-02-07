package pl.edu.wit.application.exception.auth_details;

import pl.edu.wit.application.exception.DomainException;

public class AuthDetailsNotFoundException extends DomainException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
