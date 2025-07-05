package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

@Service
public class ServiceResponseAdapter {

    private final ServiceFacade serviceFacade;

    public ServiceResponseAdapter(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    public void create(ServiceCreateCommand command) {
        serviceFacade.create(command);
    }

    public void update(String serviceId, ServiceUpdateCommand command) {
        serviceFacade.update(serviceId, command);
    }

    public ServiceResponse findOne(String serviceId) {
        return ServiceResponse.of(serviceFacade.findOne(serviceId));
    }

    public PagedResponse<ServiceResponse> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                serviceFacade.findAll(findQuery, pageable)
                        .map(ServiceResponse::of)
        );
    }

}
