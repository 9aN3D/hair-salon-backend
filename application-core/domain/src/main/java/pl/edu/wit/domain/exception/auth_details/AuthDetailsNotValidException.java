package pl.edu.wit.domain.exception.auth_details;

public class AuthDetailsNotValidException extends RuntimeException {

    public AuthDetailsNotValidException(String message) {
        super(message);
    }

}
