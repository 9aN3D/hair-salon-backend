package pl.edu.wit.hairsalon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withId;

class AppServiceFacade implements ServiceFacade {

    private final ServicePort servicePort;
    private final ServiceCreator serviceCreator;
    private final ServiceUpdater serviceUpdater;

    AppServiceFacade(ServicePort servicePort, ServiceCreator serviceCreator, ServiceUpdater serviceUpdater) {
        this.servicePort = servicePort;
        this.serviceCreator = serviceCreator;
        this.serviceUpdater = serviceUpdater;
    }

    @Override
    public String create(ServiceCreateCommand command) {
        requireNonNull(command, "Service create command must not be null");
        var service = serviceCreator.create(command);
        return servicePort.save(service.toDto());
    }

    @Override
    public void update(String serviceId, ServiceUpdateCommand command) {
        requireNonNull(serviceId, "Service id must not be null");
        requireNonNull(command, "Service update command must not be null");
        var updatedService = serviceUpdater.update(serviceId, command);
        servicePort.save(updatedService.toDto());
    }

    @Override
    public ServiceDto findOne(String serviceId) {
        requireNonNull(serviceId, "Service id must not be null");
        return servicePort.findOneOrThrow(withId(serviceId));
    }

    @Override
    public Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Service find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return servicePort.findAll(findQuery, pageable);
    }

    @Override
    public long count(ServiceFindQuery findQuery) {
        requireNonNull(findQuery, "Service find query must not be null");
        return servicePort.count(findQuery);
    }

}
