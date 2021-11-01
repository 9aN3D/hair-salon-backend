package pl.edu.wit.hairsalon.authdetails.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class AuthDetailsNotFoundException extends DomainException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
