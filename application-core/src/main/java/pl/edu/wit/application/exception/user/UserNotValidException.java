package pl.edu.wit.application.exception.user;

import pl.edu.wit.application.exception.DomainException;

public class UserNotValidException extends DomainException {

    public UserNotValidException(String message) {
        super(message);
    }

}
