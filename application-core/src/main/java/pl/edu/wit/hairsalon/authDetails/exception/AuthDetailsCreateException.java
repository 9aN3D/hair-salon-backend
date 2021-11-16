package pl.edu.wit.hairsalon.authDetails.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class AuthDetailsCreateException extends DomainException {

    public AuthDetailsCreateException(String message) {
        super(message);
    }

}
