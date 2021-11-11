package pl.edu.wit.hairsalon.reservation.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class ReservationNotFoundException extends DomainException {

    public ReservationNotFoundException(String message) {
        super(message);
    }

}
