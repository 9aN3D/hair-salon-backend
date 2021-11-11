package pl.edu.wit.hairsalon.reservation.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class ReservationCalculationException extends DomainException {

    public ReservationCalculationException(String message) {
        super(message);
    }

}
