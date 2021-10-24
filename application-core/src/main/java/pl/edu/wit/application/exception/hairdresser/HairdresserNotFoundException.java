package pl.edu.wit.application.exception.hairdresser;

import pl.edu.wit.application.exception.DomainException;

public class HairdresserNotFoundException extends DomainException {

    public HairdresserNotFoundException(String message) {
        super(message);
    }

}
