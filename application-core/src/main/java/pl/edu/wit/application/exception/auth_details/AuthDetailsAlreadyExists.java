package pl.edu.wit.application.exception.auth_details;

import pl.edu.wit.application.exception.DomainException;

public class AuthDetailsAlreadyExists extends DomainException {

    public AuthDetailsAlreadyExists(String message) {
        super(message);
    }

}
