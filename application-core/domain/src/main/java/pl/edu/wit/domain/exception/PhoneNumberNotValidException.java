package pl.edu.wit.domain.exception;

public class PhoneNumberNotValidException extends RuntimeException {

    public PhoneNumberNotValidException(String message) {
        super(message);
    }

}
