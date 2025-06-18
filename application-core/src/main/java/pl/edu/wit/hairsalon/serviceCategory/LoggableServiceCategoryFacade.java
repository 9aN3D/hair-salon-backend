package pl.edu.wit.hairsalon.serviceCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;

class LoggableServiceCategoryFacade implements ServiceCategoryFacade {

    private final Logger log;
    private final ServiceCategoryFacade delegate;

    LoggableServiceCategoryFacade(ServiceCategoryFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableServiceCategoryFacade.class);
        this.delegate = delegate;
    }

    @Override
    public void create(ServiceCategoryCreateCommand command) {
        log.trace("Creating service category {command: {}}", command);
        delegate.create(command);
        log.info("Created service category {command: {}}", command);
    }

    @Override
    public void update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        log.trace("Updating service category {id: {}, command: {}}", serviceCategoryId, command);
        delegate.update(serviceCategoryId, command);
        log.info("Updated service category {id: {}, command: {}}", serviceCategoryId, command);
    }

    @Override
    public ServiceCategoryDto findOne(String serviceCategoryId) {
        log.trace("Getting service category {id: {}}", serviceCategoryId);
        var result = delegate.findOne(serviceCategoryId);
        log.info("Got service category {dto: {}}", result);
        return result;
    }

    @Override
    public Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        log.trace("Searching service categories {findQuery: {}, pageable: {}}", findQuery, pageable);
        var result = delegate.findAll(findQuery, pageable);
        log.info("Searched service categories {numberOfElements: {}}", result.getNumberOfElements());
        return result;
    }

}
