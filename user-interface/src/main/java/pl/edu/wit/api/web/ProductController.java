package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.facade.ProductFacade;
import pl.edu.wit.api.response.ProductResponse;
import pl.edu.wit.application.command.product.ProductCreateCommand;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.ProductFindQuery;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class ProductController {

    private final ProductFacade productFacade;

    @PostMapping(value = "/admin/products", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void create(@NotNull @RequestBody ProductCreateCommand command) {
        productFacade.create(command);
    }

    @PutMapping(value = "/admin/products/{productId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable String productId, @NotNull @RequestBody ProductUpdateCommand command) {
        productFacade.update(productId, command);
    }

    @GetMapping(value = "/admin/products/{productId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ProductResponse findOne(@PathVariable String productId) {
        return productFacade.findOne(productId);
    }

    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public PageSlice<ProductResponse> findAll(@NotNull ProductFindQuery findQuery, @NotNull @ParameterObject @PageableDefault(size = 100, sort = "name") Pageable pageable) {
        return productFacade.findAll(findQuery, pageable);
    }

}
