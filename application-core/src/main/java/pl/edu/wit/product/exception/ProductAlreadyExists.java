package pl.edu.wit.product.exception;

import pl.edu.wit.common.exception.DomainException;

public class ProductAlreadyExists extends DomainException {

    public ProductAlreadyExists(String message) {
        super(message);
    }

}
