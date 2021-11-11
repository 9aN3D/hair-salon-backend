package pl.edu.wit.hairsalon.appointment.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class AppointmentNotFoundException extends DomainException {

    public AppointmentNotFoundException(String message) {
        super(message);
    }

}
