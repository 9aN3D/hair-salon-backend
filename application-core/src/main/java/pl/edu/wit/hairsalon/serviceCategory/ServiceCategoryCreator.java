package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

class ServiceCategoryCreator {

    private final IdGenerator idGenerator;
    private final ServiceCategoryCommandHandlers serviceCategoryCreateCommandHandler;

    ServiceCategoryCreator(IdGenerator idGenerator, ServiceCategoryCommandHandlers serviceCategoryCreateCommandHandler) {
        this.idGenerator = idGenerator;
        this.serviceCategoryCreateCommandHandler = serviceCategoryCreateCommandHandler;
    }

    ServiceCategory create(ServiceCategoryCreateCommand command) {
        serviceCategoryCreateCommandHandler.handle(command);
        return createNewCategory(command).validate();
    }

    private ServiceCategory createNewCategory(ServiceCategoryCreateCommand command) {
        return ServiceCategory.builder()
                .id(idGenerator.generate())
                .name(command.name())
                .order(command.order())
                .status(ServiceCategoryStatus.valueOf(command.status().name()))
                .itemIds(command.serviceIds())
                .build();
    }

}
