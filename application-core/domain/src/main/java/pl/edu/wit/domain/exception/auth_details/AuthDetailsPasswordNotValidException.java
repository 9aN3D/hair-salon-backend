package pl.edu.wit.domain.exception.auth_details;

import pl.edu.wit.domain.exception.DomainException;

public class AuthDetailsPasswordNotValidException extends DomainException {

    public AuthDetailsPasswordNotValidException(String message) {
        super(message);
    }

}
