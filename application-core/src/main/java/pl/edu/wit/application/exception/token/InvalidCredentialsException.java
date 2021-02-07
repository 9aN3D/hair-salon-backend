package pl.edu.wit.application.exception.token;

import pl.edu.wit.application.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
