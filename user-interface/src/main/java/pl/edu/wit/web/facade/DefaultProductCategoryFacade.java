package pl.edu.wit.web.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.web.mapper.PageableParamsMapper;
import pl.edu.wit.web.response.ProductCategoryResponse;
import pl.edu.wit.web.response.ProductResponse;
import pl.edu.wit.product.command.ProductCategoryCreateCommand;
import pl.edu.wit.product.command.ProductCategoryUpdateCommand;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.port.primary.ProductCategoryService;
import pl.edu.wit.product.port.primary.ProductService;
import pl.edu.wit.product.query.ProductCategoryFindQuery;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static pl.edu.wit.common.query.PageableParamsQuery.maxPageableParamsQuery;
import static pl.edu.wit.product.query.ProductFindQuery.ofProductCategoryIds;

@Service
@RequiredArgsConstructor
public class DefaultProductCategoryFacade implements ProductCategoryFacade {

    private final ProductCategoryService productCategoryService;
    private final ProductService productService;
    private final PageableParamsMapper pageableParamsMapper;

    @Override
    public void create(ProductCategoryCreateCommand command) {
        productCategoryService.create(command);
    }

    @Override
    public void update(String productCategoryId, ProductCategoryUpdateCommand command) {
        productCategoryService.update(productCategoryId, command);
    }

    @Override
    public PageSlice<ProductCategoryResponse> findAll(ProductCategoryFindQuery findQuery, Pageable pageable) {
        var productCategoryPage = productCategoryService.findAll(findQuery, pageableParamsMapper.toPageableParamsQuery(pageable))
                .map(ProductCategoryResponse::of);
        return ifProductCategoryHasContentPrepareResponse(productCategoryPage);
    }

    private PageSlice<ProductCategoryResponse> ifProductCategoryHasContentPrepareResponse(PageSlice<ProductCategoryResponse> productCategoryPage) {
        if (productCategoryPage.hasContent()) {
            var productCategoryIdToProductCategory = collectProductCategoryIds(productCategoryPage);
            var productPage = productService.findAll(ofProductCategoryIds(productCategoryIdToProductCategory), maxPageableParamsQuery())
                    .map(ProductResponse::of);
            if (productPage.hasContent()) {
                var productCategoryIdToProducts = collectProductCategoryIdToProducts(productPage);
                return productCategoryPage.map(productCategory -> toProductCategoryResponse(productCategory, productCategoryIdToProducts));
            }
        }
        return productCategoryPage;
    }

    private Set<String> collectProductCategoryIds(PageSlice<ProductCategoryResponse> productCategoryPage) {
        return productCategoryPage.getContent()
                .stream()
                .map(ProductCategoryResponse::getId)
                .collect(Collectors.toSet());
    }

    private Map<String, Set<ProductResponse>> collectProductCategoryIdToProducts(PageSlice<ProductResponse> productPage) {
        return productPage.getContent()
                .stream()
                .collect(groupingBy(product -> product.getCategory().getId(), toSet()));
    }

    private ProductCategoryResponse toProductCategoryResponse(ProductCategoryResponse productCategoryResponse, Map<String, Set<ProductResponse>> productCategoryIdToProducts) {
        var products = productCategoryIdToProducts.getOrDefault(productCategoryResponse.getId(), new HashSet<>());
        productCategoryResponse.addProducts(products);
        return productCategoryResponse;
    }

}
