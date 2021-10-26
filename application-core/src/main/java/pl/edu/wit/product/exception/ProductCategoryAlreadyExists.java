package pl.edu.wit.product.exception;

import pl.edu.wit.common.exception.DomainException;

public class ProductCategoryAlreadyExists extends DomainException {

    public ProductCategoryAlreadyExists(String message) {
        super(message);
    }

}
