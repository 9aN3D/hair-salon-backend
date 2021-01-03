package pl.edu.wit.auth_details.shared.exception;

public class AuthDetailsNotFoundException extends RuntimeException {

    public AuthDetailsNotFoundException(String message) {
        super(message);
    }

}
