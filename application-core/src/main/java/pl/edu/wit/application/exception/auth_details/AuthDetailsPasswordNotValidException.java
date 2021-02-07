package pl.edu.wit.application.exception.auth_details;

import pl.edu.wit.application.exception.DomainException;

public class AuthDetailsPasswordNotValidException extends DomainException {

    public AuthDetailsPasswordNotValidException(String message) {
        super(message);
    }

}
