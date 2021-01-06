package pl.edu.wit.domain.exception.auth_details;

public class AuthDetailsNotFoundException extends RuntimeException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
