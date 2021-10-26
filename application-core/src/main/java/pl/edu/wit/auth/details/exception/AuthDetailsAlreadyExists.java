package pl.edu.wit.auth.details.exception;

import pl.edu.wit.common.exception.DomainException;

public class AuthDetailsAlreadyExists extends DomainException {

    public AuthDetailsAlreadyExists(String message) {
        super(message);
    }

}
