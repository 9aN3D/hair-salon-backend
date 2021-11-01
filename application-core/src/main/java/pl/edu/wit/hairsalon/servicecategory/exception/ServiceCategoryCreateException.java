package pl.edu.wit.hairsalon.servicecategory.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class ServiceCategoryCreateException extends DomainException {

    public ServiceCategoryCreateException(String message) {
        super(message);
    }

}
