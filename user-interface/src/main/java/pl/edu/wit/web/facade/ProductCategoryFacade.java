package pl.edu.wit.web.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.web.response.ProductCategoryResponse;
import pl.edu.wit.product.command.ProductCategoryCreateCommand;
import pl.edu.wit.product.command.ProductCategoryUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.query.ProductCategoryFindQuery;

public interface ProductCategoryFacade {

    void create(ProductCategoryCreateCommand command);

    void update(String productCategoryId, ProductCategoryUpdateCommand command);

    PageSlice<ProductCategoryResponse> findAll(ProductCategoryFindQuery findQuery, Pageable pageable);

}
