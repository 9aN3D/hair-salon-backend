package pl.edu.wit.hairsalon.serviceCategory.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ServiceCategoryCreateException extends DomainException {

    public ServiceCategoryCreateException(String message) {
        super(message);
    }

}
