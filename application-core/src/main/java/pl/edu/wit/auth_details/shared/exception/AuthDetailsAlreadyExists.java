package pl.edu.wit.auth_details.shared.exception;

public class AuthDetailsAlreadyExists extends RuntimeException {

    public AuthDetailsAlreadyExists(String message) {
        super(message);
    }

}
