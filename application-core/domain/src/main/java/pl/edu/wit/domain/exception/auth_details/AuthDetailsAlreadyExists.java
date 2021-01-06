package pl.edu.wit.domain.exception.auth_details;

public class AuthDetailsAlreadyExists extends RuntimeException {

    public AuthDetailsAlreadyExists(String message) {
        super(message);
    }

}
