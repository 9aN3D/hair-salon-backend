package pl.edu.wit.product.port.primary;

import pl.edu.wit.product.command.ProductCreateCommand;
import pl.edu.wit.product.command.ProductUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductFindQuery;

public interface ProductService {

    void create(ProductCreateCommand command);

    void update(String productId, ProductUpdateCommand command);

    ProductDto findOne(String productId);

    PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery);

}
