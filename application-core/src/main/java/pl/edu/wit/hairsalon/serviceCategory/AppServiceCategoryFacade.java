package pl.edu.wit.hairsalon.serviceCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;

import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withCategoryId;
import java.util.Objects;

class AppServiceCategoryFacade implements ServiceCategoryFacade {

    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryCreator serviceCategoryCreator;
    private final ServiceCategoryUpdater serviceCategoryUpdater;

    AppServiceCategoryFacade(ServiceCategoryPort serviceCategoryPort, ServiceCategoryCreator serviceCategoryCreator, ServiceCategoryUpdater serviceCategoryUpdater) {
        this.serviceCategoryPort = serviceCategoryPort;
        this.serviceCategoryCreator = serviceCategoryCreator;
        this.serviceCategoryUpdater = serviceCategoryUpdater;
    }

    @Override
    public void create(ServiceCategoryCreateCommand command) {
        Objects.requireNonNull(command, "Service category create command must not be null");
        var serviceCategory = serviceCategoryCreator.create(command);
        serviceCategoryPort.save(serviceCategory.toDto());
    }

    @Override
    public void update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        Objects.requireNonNull(serviceCategoryId, "Service category id must not be null");
        Objects.requireNonNull(command, "Service category update command must not be null");
        var updatedCategory = serviceCategoryUpdater.update(serviceCategoryId, command);
        serviceCategoryPort.save(updatedCategory.toDto());
    }

    @Override
    public ServiceCategoryDto findOne(String serviceCategoryId) {
        Objects.requireNonNull(serviceCategoryId, "Service category id must not be null");
        return serviceCategoryPort.findOneOrThrow(withCategoryId(serviceCategoryId));
    }

    @Override
    public Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        Objects.requireNonNull(findQuery, "Service category find query must not be null");
        Objects.requireNonNull(pageable, "Pageable must not be null");
        return serviceCategoryPort.findAll(findQuery, pageable);
    }
}
