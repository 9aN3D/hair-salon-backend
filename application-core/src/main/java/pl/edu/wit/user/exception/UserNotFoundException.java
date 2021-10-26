package pl.edu.wit.user.exception;

import pl.edu.wit.common.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
