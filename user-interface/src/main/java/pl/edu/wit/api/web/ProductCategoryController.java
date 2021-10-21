package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.facade.ProductCategoryFacade;
import pl.edu.wit.api.response.ProductCategoryResponse;
import pl.edu.wit.application.command.product.ProductCategoryCreateCommand;
import pl.edu.wit.application.command.product.ProductCategoryUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.ProductCategoryFindQuery;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ProductCategoryController {

    private final ProductCategoryFacade productCategoryFacade;

    @PostMapping(value = "/admin/products/categories", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void create(@NotNull @RequestBody ProductCategoryCreateCommand command) {
        productCategoryFacade.create(command);
    }

    @PutMapping(value = "/admin/products/categories/{productCategoryId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable String productCategoryId, @NotNull @RequestBody ProductCategoryUpdateCommand command) {
        productCategoryFacade.update(productCategoryId, command);
    }

    @GetMapping(value = "/products/categories", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public PageSlice<ProductCategoryResponse> findAll(@NotNull ProductCategoryFindQuery findQuery, @NotNull Pageable pageable) {
        return productCategoryFacade.findAll(findQuery, pageable);
    }

}
