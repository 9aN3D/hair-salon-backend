package pl.edu.wit.product.exception;

import pl.edu.wit.common.exception.DomainException;

public class ProductNotFoundException extends DomainException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
