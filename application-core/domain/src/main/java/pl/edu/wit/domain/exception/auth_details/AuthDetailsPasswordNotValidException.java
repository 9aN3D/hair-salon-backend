package pl.edu.wit.domain.exception.auth_details;

public class AuthDetailsPasswordNotValidException extends RuntimeException {

    public AuthDetailsPasswordNotValidException(String message) {
        super(message);
    }

}
