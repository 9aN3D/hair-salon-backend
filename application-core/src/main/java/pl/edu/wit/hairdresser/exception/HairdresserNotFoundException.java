package pl.edu.wit.hairdresser.exception;

import pl.edu.wit.common.exception.DomainException;

public class HairdresserNotFoundException extends DomainException {

    public HairdresserNotFoundException(String message) {
        super(message);
    }

}
