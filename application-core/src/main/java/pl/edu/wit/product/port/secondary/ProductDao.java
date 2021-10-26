package pl.edu.wit.product.port.secondary;

import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductFindQuery;

import java.util.Optional;

public interface ProductDao {

    String save(ProductDto product);

    Optional<ProductDto> findOne(ProductFindQuery findQuery);

    PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery);

}
