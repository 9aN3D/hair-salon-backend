package pl.edu.wit.api.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.api.response.ProductCategoryResponse;
import pl.edu.wit.application.command.product.ProductCategoryCreateCommand;
import pl.edu.wit.application.command.product.ProductCategoryUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.ProductCategoryFindQuery;

public interface ProductCategoryFacade {

    void create(ProductCategoryCreateCommand command);

    void update(String productCategoryId, ProductCategoryUpdateCommand command);

    PageSlice<ProductCategoryResponse> findAll(ProductCategoryFindQuery findQuery, Pageable pageable);

}
