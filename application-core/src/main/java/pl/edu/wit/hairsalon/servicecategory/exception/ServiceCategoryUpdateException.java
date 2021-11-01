package pl.edu.wit.hairsalon.servicecategory.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class ServiceCategoryUpdateException extends DomainException {

    public ServiceCategoryUpdateException(String message) {
        super(message);
    }

}
