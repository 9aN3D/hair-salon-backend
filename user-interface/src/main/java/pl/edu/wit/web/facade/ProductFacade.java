package pl.edu.wit.web.facade;

import org.springframework.data.domain.Pageable;
import pl.edu.wit.web.response.ProductResponse;
import pl.edu.wit.product.command.ProductCreateCommand;
import pl.edu.wit.product.command.ProductUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.query.ProductFindQuery;

public interface ProductFacade {

    void create(ProductCreateCommand command);

    void update(String productId, ProductUpdateCommand command);

    ProductResponse findOne(String productId);

    PageSlice<ProductResponse> findAll(ProductFindQuery findQuery, Pageable pageable);

}
