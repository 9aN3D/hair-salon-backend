package pl.edu.wit.hairsalon.web;

import jakarta.validation.constraints.NotNull;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ServiceResponseAdapter;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
class ServiceController {

    private final ServiceResponseAdapter serviceResponseAdapter;

    ServiceController(ServiceResponseAdapter serviceResponseAdapter) {
        this.serviceResponseAdapter = serviceResponseAdapter;
    }

    @PostMapping(value = "/admin/services", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void create(@NotNull @RequestBody ServiceCreateCommand command) {
        serviceResponseAdapter.create(command);
    }

    @PutMapping(value = "/admin/services/{serviceId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable String serviceId, @NotNull @RequestBody ServiceUpdateCommand command) {
        serviceResponseAdapter.update(serviceId, command);
    }

    @GetMapping(value = "/admin/services/{serviceId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    ServiceResponse findOne(@PathVariable String serviceId) {
        return serviceResponseAdapter.findOne(serviceId);
    }

    @GetMapping(value = "/services", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<ServiceResponse> findAll(@NotNull ServiceFindQuery findQuery, @NotNull @ParameterObject @PageableDefault(size = 100, sort = "name") Pageable pageable) {
        return serviceResponseAdapter.findAll(findQuery, pageable);
    }

}
