package pl.edu.wit.application.exception.auth_details;

import pl.edu.wit.application.exception.DomainException;

public class AuthDetailsNotValidException extends DomainException {

    public AuthDetailsNotValidException(String message) {
        super(message);
    }

}
