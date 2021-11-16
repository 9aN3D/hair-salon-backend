package pl.edu.wit.hairsalon.service.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ServiceNotFoundException extends DomainException {

    public ServiceNotFoundException(String message) {
        super(message);
    }

}
