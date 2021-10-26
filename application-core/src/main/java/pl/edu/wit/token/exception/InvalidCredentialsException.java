package pl.edu.wit.token.exception;

import pl.edu.wit.common.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
