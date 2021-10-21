package pl.edu.wit.application.domain.usecase.product;

import pl.edu.wit.application.command.product.ProductCreateCommand;

public interface ProductCreateUseCase {

    String create(ProductCreateCommand command);

}
