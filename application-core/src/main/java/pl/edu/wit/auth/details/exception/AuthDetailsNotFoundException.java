package pl.edu.wit.auth.details.exception;

import pl.edu.wit.common.exception.DomainException;

public class AuthDetailsNotFoundException extends DomainException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
