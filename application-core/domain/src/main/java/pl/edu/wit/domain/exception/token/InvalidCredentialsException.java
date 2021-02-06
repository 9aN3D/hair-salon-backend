package pl.edu.wit.domain.exception.token;

import pl.edu.wit.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
