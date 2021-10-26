package pl.edu.wit.product.port.primary;

import pl.edu.wit.product.command.ProductCategoryCreateCommand;
import pl.edu.wit.product.command.ProductCategoryUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductCategoryFindQuery;

public interface ProductCategoryService {

    void create(ProductCategoryCreateCommand command);

    void update(String productCategoryId, ProductCategoryUpdateCommand command);

    ProductCategoryDto findOne(String productCategoryId);

    PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery);

}
