package pl.edu.wit.application.domain.usecase.product;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.application.command.product.ProductCreateCommand;
import pl.edu.wit.application.domain.model.Money;
import pl.edu.wit.application.domain.model.product.Product;
import pl.edu.wit.application.domain.model.product.ProductCategory;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.application.exception.product.ProductAlreadyExists;
import pl.edu.wit.application.exception.product.ProductCategoryNotFoundException;
import pl.edu.wit.application.port.secondary.IdGenerator;
import pl.edu.wit.application.port.secondary.ProductCategoryDao;
import pl.edu.wit.application.port.secondary.ProductDao;
import pl.edu.wit.application.query.ProductFindQuery;

import java.time.Duration;

import static java.lang.String.format;
import static pl.edu.wit.application.domain.model.product.ProductType.SERVICE;
import static pl.edu.wit.application.query.ProductCategoryFindQuery.ofProductCategoryId;

@RequiredArgsConstructor
public class DefaultProductCreateUseCase implements ProductCreateUseCase {

    private final ProductCategoryDao productCategoryDao;
    private final ProductDao productDao;
    private final IdGenerator idGenerator;

    @Override
    public String create(ProductCreateCommand command) {
        throwIfExistProductName(command.getName());
        var categoryDto = getOneFromDaoOrThrow(command.getProductCategoryId());
        var product = createNewProduct(categoryDto, command);
        return productDao.save(product.toDto());
    }

    private void throwIfExistProductName(String name) {
        if (existByProductName(name)) {
            throw new ProductAlreadyExists(
                    format("Product already exists by name: %s", name)
            );
        }
    }

    private Boolean existByProductName(String name) {
        return productDao.findOne(ProductFindQuery.ofProductName(name)).isPresent();
    }

    private ProductCategoryDto getOneFromDaoOrThrow(String productCategoryId) {
        return productCategoryDao.findOne(ofProductCategoryId(productCategoryId))
                .orElseThrow(() -> new ProductCategoryNotFoundException(
                                format("Product category not found by id: %s", productCategoryId)
                        )
                );
    }

    public Product createNewProduct(ProductCategoryDto categoryDto, ProductCreateCommand command) {
        return Product.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .type(SERVICE)
                .price(new Money(command.getPrice()))
                .duration(Duration.ofMinutes(command.getDurationInMinutes()))
                .category(new ProductCategory(categoryDto))
                .build();
    }

}
