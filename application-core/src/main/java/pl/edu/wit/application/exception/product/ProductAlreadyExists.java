package pl.edu.wit.application.exception.product;

import pl.edu.wit.application.exception.DomainException;

public class ProductAlreadyExists extends DomainException {

    public ProductAlreadyExists(String message) {
        super(message);
    }

}
