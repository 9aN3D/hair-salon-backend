package pl.edu.wit.hairsalon.serviceCategory.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class ServiceCategoryUpdateException extends DomainException {

    public ServiceCategoryUpdateException(String message) {
        super(message);
    }

}
