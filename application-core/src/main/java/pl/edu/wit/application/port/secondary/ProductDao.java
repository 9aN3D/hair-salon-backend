package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.ProductDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.ProductFindQuery;

import java.util.Optional;

public interface ProductDao {

    String save(ProductDto product);

    Optional<ProductDto> findOne(ProductFindQuery findQuery);

    PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery);

}
