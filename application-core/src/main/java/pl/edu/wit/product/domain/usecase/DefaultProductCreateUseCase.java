package pl.edu.wit.product.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.product.command.ProductCreateCommand;
import pl.edu.wit.common.domain.Money;
import pl.edu.wit.product.domain.Product;
import pl.edu.wit.product.domain.ProductCategory;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.product.exception.ProductAlreadyExists;
import pl.edu.wit.product.exception.ProductCategoryNotFoundException;
import pl.edu.wit.common.port.secondary.IdGenerator;
import pl.edu.wit.product.port.secondary.ProductCategoryDao;
import pl.edu.wit.product.port.secondary.ProductDao;
import pl.edu.wit.product.query.ProductFindQuery;

import java.time.Duration;

import static java.lang.String.format;
import static pl.edu.wit.product.domain.ProductType.SERVICE;
import static pl.edu.wit.product.query.ProductCategoryFindQuery.ofProductCategoryId;

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
