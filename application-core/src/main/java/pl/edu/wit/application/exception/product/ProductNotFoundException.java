package pl.edu.wit.application.exception.product;

import pl.edu.wit.application.exception.DomainException;

public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
