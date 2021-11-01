package pl.edu.wit.hairsalon.token.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
