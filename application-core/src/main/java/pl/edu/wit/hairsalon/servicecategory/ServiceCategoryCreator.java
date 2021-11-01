package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.exception.ServiceCategoryCreateException;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import java.util.Set;

import static java.lang.String.format;
import static pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery.withName;

@RequiredArgsConstructor
class ServiceCategoryCreator {

    private final IdGenerator idGenerator;
    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryItemIds serviceCategoryItemIds;

    ServiceCategory create(ServiceCategoryCreateCommand command) {
        throwIfServiceIdsNotExists(command.getServiceIds());
        throwIfCategoryNameExist(command.getName());
        return createNewCategory(command).validate();
    }

    private void throwIfCategoryNameExist(String name) {
        if (serviceCategoryPort.findOne(withName(name)).isPresent()) {
            throw new ServiceCategoryCreateException(
                    format("Service category already exists by name: %s", name)
            );
        }
    }

    private void throwIfServiceIdsNotExists(Set<String> serviceIds) {
        if (serviceCategoryItemIds.isExists(serviceIds)) {
            throw new ServiceCategoryCreateException(
                    format("Service ids is not correct: %s", serviceIds)
            );
        }
    }

    public ServiceCategory createNewCategory(ServiceCategoryCreateCommand command) {
        return ServiceCategory.builder()
                .id(idGenerator.generate())
                .name(command.getName())
                .status(ServiceCategoryStatus.valueOf(command.getStatus().name()))
                .itemIds(command.getServiceIds())
                .build();
    }

}
