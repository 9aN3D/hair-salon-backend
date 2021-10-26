package pl.edu.wit.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.product.command.ProductCategoryCreateCommand;
import pl.edu.wit.product.command.ProductCategoryUpdateCommand;
import pl.edu.wit.product.domain.ProductCategory;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.product.exception.ProductCategoryAlreadyExists;
import pl.edu.wit.product.exception.ProductCategoryNotFoundException;
import pl.edu.wit.product.port.primary.ProductCategoryService;
import pl.edu.wit.common.port.secondary.IdGenerator;
import pl.edu.wit.product.port.secondary.ProductCategoryDao;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductCategoryFindQuery;

import static java.lang.String.format;
import static pl.edu.wit.product.query.ProductCategoryFindQuery.ofProductCategoryId;
import static pl.edu.wit.product.query.ProductCategoryFindQuery.ofProductCategoryName;

@Slf4j
@RequiredArgsConstructor
public class AppProductCategoryService implements ProductCategoryService {

    private final ProductCategoryDao dao;
    private final IdGenerator idGenerator;

    @Override
    public void create(ProductCategoryCreateCommand command) {
        log.trace("Creating product category {command: {}}", command);
        throwIfExistProductCategoryName(command.getName());
        var productCategory = createNewCategory(command);
        var savedProductCategoryId = dao.save(productCategory.toDto());
        log.info("Created product category {id: {}}", savedProductCategoryId);
    }

    @Override
    public void update(String productCategoryId, ProductCategoryUpdateCommand command) {
        log.trace("Updating product category {productCategoryId: {}, command: {}}", productCategoryId, command);
        var productCategory = new ProductCategory(getOneFromDaoOrThrow(productCategoryId));
        var updatedCategory = productCategory.update(command);
        var save = dao.save(updatedCategory.toDto());
        log.info("Updated product category {productCategory: {}}", productCategory);
    }

    @Override
    public ProductCategoryDto findOne(String productCategoryId) {
        log.trace("Getting product category {id: {}}", productCategoryId);
        var productCategoryDto = getOneFromDaoOrThrow(productCategoryId);
        log.info("Got product category {dto: {}}", productCategoryDto);
        return productCategoryDto;
    }

    @Override
    public PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery) {
        log.trace("Searching product categories {findQuery: {}, pageableQuery: {}}", findQuery, pageableQuery);
        var page = dao.findAll(findQuery, pageableQuery);
        log.info("Searched product categories {contentTotalElements: {}, contentSize: {}}", page.getTotalElements(), page.getSize());
        return page;
    }

    private void throwIfExistProductCategoryName(String name) {
        if (existByProductCategoryName(name)) {
            throw new ProductCategoryAlreadyExists(
                    format("Product category already exists by name: %s", name)
            );
        }
    }

    private Boolean existByProductCategoryName(String name) {
        return dao.findOne(ofProductCategoryName(name)).isPresent();
    }

    public ProductCategory createNewCategory(ProductCategoryCreateCommand command) {
        return ProductCategory.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .build();
    }

    private ProductCategoryDto getOneFromDaoOrThrow(String productCategoryId) {
        return dao.findOne(ofProductCategoryId(productCategoryId))
                .orElseThrow(() -> new ProductCategoryNotFoundException(
                                format("Product category not found by id: %s", productCategoryId)
                        )
                );
    }

}
