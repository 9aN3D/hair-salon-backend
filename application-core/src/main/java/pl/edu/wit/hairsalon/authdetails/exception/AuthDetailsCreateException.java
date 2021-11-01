package pl.edu.wit.hairsalon.authdetails.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class AuthDetailsCreateException extends DomainException {

    public AuthDetailsCreateException(String message) {
        super(message);
    }

}
