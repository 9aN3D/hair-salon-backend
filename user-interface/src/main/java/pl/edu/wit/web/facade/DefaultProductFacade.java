package pl.edu.wit.web.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.web.mapper.PageableParamsMapper;
import pl.edu.wit.web.response.ProductResponse;
import pl.edu.wit.product.command.ProductCreateCommand;
import pl.edu.wit.product.command.ProductUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.port.primary.ProductService;
import pl.edu.wit.product.query.ProductFindQuery;

@Service
@RequiredArgsConstructor
public class DefaultProductFacade implements ProductFacade {

    private final ProductService productService;
    private final PageableParamsMapper pageableParamsMapper;

    @Override
    public void create(ProductCreateCommand command) {
        productService.create(command);
    }

    @Override
    public void update(String productId, ProductUpdateCommand command) {
        productService.update(productId, command);
    }

    @Override
    public ProductResponse findOne(String productId) {
        return ProductResponse.of(productService.findOne(productId));
    }

    @Override
    public PageSlice<ProductResponse> findAll(ProductFindQuery findQuery, Pageable pageable) {
        return productService.findAll(findQuery, pageableParamsMapper.toPageableParamsQuery(pageable))
                .map(ProductResponse::of);
    }

}
