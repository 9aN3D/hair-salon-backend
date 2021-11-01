package pl.edu.wit.hairsalon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.service.query.ServiceFindQuery.withId;

@Slf4j
@RequiredArgsConstructor
class AppServiceFacade implements ServiceFacade {

    private final ServicePort servicePort;
    private final ServiceCreator serviceCreator;
    private final ServiceUpdater serviceUpdater;

    @Override
    public String create(ServiceCreateCommand command) {
        log.trace("Creating service {command: {}}", command);
        requireNonNull(command, "Service create command must not be null");
        var service = serviceCreator.create(command);
        var serviceId = servicePort.save(service.toDto());
        log.info("Created service {id: {}, service: {}}", serviceId, service);
        return serviceId;
    }

    @Override
    public void update(String serviceId, ServiceUpdateCommand command) {
        log.trace("Updating service {id: {}, command: {}}", serviceId, command);
        requireNonNull(serviceId, "Service id must not be null");
        requireNonNull(command, "Service update command must not be null");
        var updatedService = serviceUpdater.update(serviceId, command);
        var savedServiceId = servicePort.save(updatedService.toDto());
        log.info("Updated service {id: {}, updatedService: {}}", savedServiceId, updatedService);
    }

    @Override
    public ServiceDto findOne(String serviceId) {
        log.trace("Getting service {id: {}}", serviceId);
        requireNonNull(serviceId, "Service id must not be null");
        var productDto = servicePort.findOneOrThrow(withId(serviceId));
        log.info("Got service {dto: {}}", productDto);
        return productDto;
    }

    @Override
    public Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        log.trace("Searching services {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Service find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = servicePort.findAll(findQuery, pageable);
        log.info("Searched services {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public long count(ServiceFindQuery findQuery) {
        log.trace("Counting services {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "Service find query must not be null");
        var count = servicePort.count(findQuery);
        log.trace("Counted services {result: {}}", count);
        return count;
    }

}
