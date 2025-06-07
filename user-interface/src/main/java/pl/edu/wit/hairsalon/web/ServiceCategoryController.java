package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ServiceCategoryResponseAdapter;
import pl.edu.wit.hairsalon.web.response.ServiceCategoryResponse;

import jakarta.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
class ServiceCategoryController {

    private final ServiceCategoryResponseAdapter serviceCategoryResponseAdapter;

    @PostMapping(value = "/admin/services/categories", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void create(@NotNull @RequestBody ServiceCategoryCreateCommand command) {
        serviceCategoryResponseAdapter.create(command);
    }

    @PutMapping(value = "/admin/services/categories/{serviceCategoryId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @SecurityRequirement(name = "hair-salon-API")
    void update(@PathVariable String serviceCategoryId, @NotNull @RequestBody ServiceCategoryUpdateCommand command) {
        serviceCategoryResponseAdapter.update(serviceCategoryId, command);
    }

    @GetMapping(value = "/services/categories", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Page<ServiceCategoryResponse> findAll(@NotNull ServiceCategoryFindQuery findQuery, @NotNull Pageable pageable) {
        return serviceCategoryResponseAdapter.findAll(findQuery, pageable);
    }

}
