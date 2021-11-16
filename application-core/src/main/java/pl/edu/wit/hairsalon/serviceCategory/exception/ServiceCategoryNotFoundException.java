package pl.edu.wit.hairsalon.serviceCategory.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ServiceCategoryNotFoundException extends DomainException {

    public ServiceCategoryNotFoundException(String message) {
        super(message);
    }

}
