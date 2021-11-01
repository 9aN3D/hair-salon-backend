package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.servicecategory.ServiceCategoryFacade;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ServiceResponseAdapter {

    private final ServiceFacade serviceFacade;

    public void create(ServiceCreateCommand command) {
        serviceFacade.create(command);
    }

    public void update(String serviceId, ServiceUpdateCommand command) {
        serviceFacade.update(serviceId, command);
    }

    public ServiceResponse findOne(String serviceId) {
        return ServiceResponse.of(serviceFacade.findOne(serviceId));
    }

    public Page<ServiceResponse> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        return serviceFacade.findAll(findQuery, pageable)
                .map(ServiceResponse::of);
    }

}
