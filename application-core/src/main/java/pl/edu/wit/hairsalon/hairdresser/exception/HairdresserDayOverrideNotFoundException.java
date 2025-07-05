package pl.edu.wit.hairsalon.hairdresser.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class HairdresserDayOverrideNotFoundException extends DomainException {

    public HairdresserDayOverrideNotFoundException(String message) {
        super(message);
    }

}
