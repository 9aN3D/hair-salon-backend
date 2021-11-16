package pl.edu.wit.hairsalon.service.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ServiceAlreadyExists extends DomainException {

    public ServiceAlreadyExists(String message) {
        super(message);
    }

}
