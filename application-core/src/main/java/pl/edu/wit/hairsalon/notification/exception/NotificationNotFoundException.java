package pl.edu.wit.hairsalon.notification.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class NotificationNotFoundException extends DomainException {

    public NotificationNotFoundException(String message) {
        super(message);
    }

}
