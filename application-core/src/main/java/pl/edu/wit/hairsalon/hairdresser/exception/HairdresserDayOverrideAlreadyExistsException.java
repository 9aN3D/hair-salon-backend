package pl.edu.wit.hairsalon.hairdresser.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class HairdresserDayOverrideAlreadyExistsException extends DomainException {

    public HairdresserDayOverrideAlreadyExistsException(String message) {
        super(message);
    }

}
