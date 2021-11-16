package pl.edu.wit.hairsalon.appointment.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class AppointmentResignException extends DomainException {

    public AppointmentResignException(String message) {
        super(message);
    }

}
