package pl.edu.wit.hairsalon.authDetails.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class AuthDetailsNotFoundException extends DomainException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
