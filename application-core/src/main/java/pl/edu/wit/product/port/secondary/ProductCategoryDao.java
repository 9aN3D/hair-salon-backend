package pl.edu.wit.product.port.secondary;

import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductCategoryFindQuery;

import java.util.Optional;

public interface ProductCategoryDao {

    String save(ProductCategoryDto productCategory);

    Optional<ProductCategoryDto> findOne(ProductCategoryFindQuery query);

    PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery);

}
