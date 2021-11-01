package pl.edu.wit.hairsalon.hairdresser.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class HairdresserNotFoundException extends DomainException {

    public HairdresserNotFoundException(String message) {
        super(message);
    }

}
