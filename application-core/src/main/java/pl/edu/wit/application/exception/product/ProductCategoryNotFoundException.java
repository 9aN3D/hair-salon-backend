package pl.edu.wit.application.exception.product;

import pl.edu.wit.application.exception.DomainException;

public class ProductCategoryNotFoundException extends DomainException {

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }

}
