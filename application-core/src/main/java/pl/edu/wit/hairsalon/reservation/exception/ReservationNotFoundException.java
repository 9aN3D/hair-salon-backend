package pl.edu.wit.hairsalon.reservation.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ReservationNotFoundException extends DomainException {

    public ReservationNotFoundException(String message) {
        super(message);
    }

}
