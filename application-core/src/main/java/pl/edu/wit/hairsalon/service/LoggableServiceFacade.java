package pl.edu.wit.hairsalon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

public class LoggableServiceFacade implements ServiceFacade {

    private final Logger log;
    private final ServiceFacade delegate;

    LoggableServiceFacade(ServiceFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableServiceFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String create(ServiceCreateCommand command) {
        log.trace("Creating service {command: {}}", command);
        var serviceId = delegate.create(command);
        log.info("Created service {command: {}, serviceId: {}}", command, serviceId);
        return serviceId;
    }

    @Override
    public void update(String serviceId, ServiceUpdateCommand command) {
        log.trace("Updating service {id: {}, command: {}}", serviceId, command);
        delegate.update(serviceId, command);
        log.info("Updated service {id: {}, command: {}}", serviceId, command);
    }

    @Override
    public ServiceDto findOne(String serviceId) {
        log.trace("Getting service {id: {}}", serviceId);
        var result = delegate.findOne(serviceId);
        log.info("Got service {dto: {}}", result);
        return result;
    }

    @Override
    public Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        log.trace("Searching services {findQuery: {}, pageable: {}}", findQuery, pageable);
        var page = delegate.findAll(findQuery, pageable);
        log.info("Searched services {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public long count(ServiceFindQuery findQuery) {
        log.trace("Counting services {findQuery: {}}", findQuery);
        var count = delegate.count(findQuery);
        log.info("Counted services {findQuery: {}, result: {}}", findQuery, count);
        return count;
    }

}
