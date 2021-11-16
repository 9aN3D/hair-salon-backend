package pl.edu.wit.hairsalon.serviceCategory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;

import java.util.Objects;

import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withCategoryId;

@Slf4j
@RequiredArgsConstructor
class AppServiceCategoryFacade implements ServiceCategoryFacade {

    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryCreator serviceCategoryCreator;
    private final ServiceCategoryUpdater serviceCategoryUpdater;

    @Override
    public void create(ServiceCategoryCreateCommand command) {
        log.trace("Creating service category {command: {}}", command);
        Objects.requireNonNull(command, "Service category create command must not be null");
        var serviceCategory = serviceCategoryCreator.create(command);
        var savedProductCategoryId = serviceCategoryPort.save(serviceCategory.toDto());
        log.info("Created service category {id: {}, category: {}}", savedProductCategoryId, serviceCategory);
    }

    @Override
    public void update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        log.trace("Updating service category {id: {}, command: {}}", serviceCategoryId, command);
        Objects.requireNonNull(serviceCategoryId, "Service category id must not be null");
        Objects.requireNonNull(command, "Service category update command must not be null");
        var updatedCategory = serviceCategoryUpdater.update(serviceCategoryId, command);
        serviceCategoryPort.save(updatedCategory.toDto());
        log.info("Updated service category {category: {}}", updatedCategory);
    }

    @Override
    public ServiceCategoryDto findOne(String serviceCategoryId) {
        log.trace("Getting service category {id: {}}", serviceCategoryId);
        Objects.requireNonNull(serviceCategoryId, "Service category id must not be null");
        var productCategoryDto = serviceCategoryPort.findOneOrThrow(withCategoryId(serviceCategoryId));
        log.info("Got service category {dto: {}}", productCategoryDto);
        return productCategoryDto;
    }

    @Override
    public Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        log.trace("Searching service categories {findQuery: {}, pageable: {}}", findQuery, pageable);
        Objects.requireNonNull(findQuery, "Service category find query must not be null");
        Objects.requireNonNull(pageable, "Pageable must not be null");
        var page = serviceCategoryPort.findAll(findQuery, pageable);
        log.info("Searched service categories {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

}
