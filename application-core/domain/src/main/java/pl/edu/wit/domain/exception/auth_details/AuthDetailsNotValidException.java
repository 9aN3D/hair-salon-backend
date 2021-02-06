package pl.edu.wit.domain.exception.auth_details;

import pl.edu.wit.domain.exception.DomainException;

public class AuthDetailsNotValidException extends DomainException {

    public AuthDetailsNotValidException(String message) {
        super(message);
    }

}
