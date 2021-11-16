package pl.edu.wit.hairsalon.hairdresser.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class HairdresserNotFoundException extends DomainException {

    public HairdresserNotFoundException(String message) {
        super(message);
    }

}
