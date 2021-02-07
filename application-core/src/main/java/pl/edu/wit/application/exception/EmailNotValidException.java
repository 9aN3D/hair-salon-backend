package pl.edu.wit.application.exception;

public class EmailNotValidException extends DomainException {

    public EmailNotValidException(String message) {
        super(message);
    }

}
