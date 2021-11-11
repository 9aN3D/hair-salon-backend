package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@RequiredArgsConstructor
class ServiceCategoryCreator {

    private final IdGenerator idGenerator;
    private final ServiceCategoryCommandHandlers serviceCategoryCreateCommandHandler;

    ServiceCategory create(ServiceCategoryCreateCommand command) {
        serviceCategoryCreateCommandHandler.handle(command);
        return createNewCategory(command).validate();
    }

    public ServiceCategory createNewCategory(ServiceCategoryCreateCommand command) {
        return ServiceCategory.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .order(command.getOrder())
                .status(ServiceCategoryStatus.valueOf(command.getStatus().name()))
                .itemIds(command.getServiceIds())
                .build();
    }

}
