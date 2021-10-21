package pl.edu.wit.application.exception.product;

import pl.edu.wit.application.exception.DomainException;

public class ProductCategoryAlreadyExists extends DomainException {

    public ProductCategoryAlreadyExists(String message) {
        super(message);
    }

}
