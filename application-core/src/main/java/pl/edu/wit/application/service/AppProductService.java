package pl.edu.wit.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.application.command.product.ProductCreateCommand;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.domain.model.product.Product;
import pl.edu.wit.application.domain.usecase.product.ProductCreateUseCase;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.ProductDto;
import pl.edu.wit.application.exception.product.ProductNotFoundException;
import pl.edu.wit.application.port.primary.ProductService;
import pl.edu.wit.application.port.secondary.ProductDao;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.ProductFindQuery;

import static java.lang.String.format;
import static pl.edu.wit.application.query.ProductFindQuery.ofProductId;

@Slf4j
@RequiredArgsConstructor
public class AppProductService implements ProductService {

    private final ProductCreateUseCase productCreateUseCase;
    private final ProductDao productDao;

    @Override
    public void create(ProductCreateCommand command) {
        log.trace("Creating product {command: {}}", command);
        var productId = productCreateUseCase.create(command);
        log.info("Created product {productId: {}}", productId);
    }

    @Override
    public void update(String productId, ProductUpdateCommand command) {
        log.trace("Updating product {productId: {}, command: {}}", productId, command);
        var product = new Product(getOneFromDaoOrThrow(productId));
        var updatedProduct = product.update(command);
        var savedProductId = productDao.save(updatedProduct.toDto());
        log.info("Updated product {savedProductId: {}, updatedProduct: {}}", savedProductId, updatedProduct);
    }

    @Override
    public ProductDto findOne(String productId) {
        log.trace("Getting product {id: {}}", productId);
        var productDto = getOneFromDaoOrThrow(productId);
        log.info("Got product {dto: {}}", productDto);
        return productDto;
    }

    @Override
    public PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery) {
        log.trace("Searching products {findQuery: {}, pageableQuery: {}}", findQuery, pageableQuery);
        var page = productDao.findAll(findQuery, pageableQuery);
        log.info("Searched products {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

    private ProductDto getOneFromDaoOrThrow(String productId) {
        return productDao.findOne(ofProductId(productId))
                .orElseThrow(() -> new ProductNotFoundException(
                                format("Product not found by id: %s", productId)
                        )
                );
    }

}
