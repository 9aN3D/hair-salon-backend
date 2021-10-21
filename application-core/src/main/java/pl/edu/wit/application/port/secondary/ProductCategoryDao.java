package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.ProductCategoryFindQuery;

import java.util.Optional;

public interface ProductCategoryDao {

    String save(ProductCategoryDto productCategory);

    Optional<ProductCategoryDto> findOne(ProductCategoryFindQuery query);

    PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery);

}
