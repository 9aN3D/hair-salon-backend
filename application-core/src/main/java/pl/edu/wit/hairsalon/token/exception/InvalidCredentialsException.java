package pl.edu.wit.hairsalon.token.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class InvalidCredentialsException extends DomainException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
