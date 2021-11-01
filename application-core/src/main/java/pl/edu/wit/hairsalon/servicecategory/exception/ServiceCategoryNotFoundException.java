package pl.edu.wit.hairsalon.servicecategory.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class ServiceCategoryNotFoundException extends DomainException {

    public ServiceCategoryNotFoundException(String message) {
        super(message);
    }

}
