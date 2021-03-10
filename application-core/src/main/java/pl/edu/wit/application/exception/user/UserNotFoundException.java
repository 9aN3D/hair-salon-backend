package pl.edu.wit.application.exception.user;

import pl.edu.wit.application.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
