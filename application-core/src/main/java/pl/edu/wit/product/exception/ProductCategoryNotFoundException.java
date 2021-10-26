package pl.edu.wit.product.exception;

import pl.edu.wit.common.exception.DomainException;

public class ProductCategoryNotFoundException extends DomainException {

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }

}
