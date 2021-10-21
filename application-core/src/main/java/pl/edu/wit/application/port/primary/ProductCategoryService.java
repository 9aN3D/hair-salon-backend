package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.product.ProductCategoryCreateCommand;
import pl.edu.wit.application.command.product.ProductCategoryUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.ProductCategoryFindQuery;

public interface ProductCategoryService {

    void create(ProductCategoryCreateCommand command);

    void update(String productCategoryId, ProductCategoryUpdateCommand command);

    ProductCategoryDto findOne(String productCategoryId);

    PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery);

}
