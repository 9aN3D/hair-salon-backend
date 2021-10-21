package pl.edu.wit.api.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.api.response.ProductResponse;
import pl.edu.wit.application.command.product.ProductCreateCommand;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.ProductFindQuery;

public interface ProductFacade {

    void create(ProductCreateCommand command);

    void update(String productId, ProductUpdateCommand command);

    ProductResponse findOne(String productId);

    PageSlice<ProductResponse> findAll(ProductFindQuery findQuery, Pageable pageable);

}
