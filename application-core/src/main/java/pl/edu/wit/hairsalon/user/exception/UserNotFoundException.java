package pl.edu.wit.hairsalon.user.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
