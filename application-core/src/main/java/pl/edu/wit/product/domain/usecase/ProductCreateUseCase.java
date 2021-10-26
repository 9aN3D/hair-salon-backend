package pl.edu.wit.product.domain.usecase;

import pl.edu.wit.product.command.ProductCreateCommand;

public interface ProductCreateUseCase {

    String create(ProductCreateCommand command);

}
