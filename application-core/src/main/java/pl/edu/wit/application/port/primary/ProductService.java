package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.product.ProductCreateCommand;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.ProductDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.ProductFindQuery;

public interface ProductService {

    void create(ProductCreateCommand command);

    void update(String productId, ProductUpdateCommand command);

    ProductDto findOne(String productId);

    PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery);

}
