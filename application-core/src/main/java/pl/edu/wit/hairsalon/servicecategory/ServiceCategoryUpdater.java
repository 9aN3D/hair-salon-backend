package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.exception.ServiceCategoryUpdateException;

import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;
import static pl.edu.wit.hairsalon.servicecategory.ServiceCategoryStatus.valueOf;
import static pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery.withCategoryId;
import static pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery.withName;

@RequiredArgsConstructor
class ServiceCategoryUpdater {

    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryItemIds serviceCategoryItemIds;

    ServiceCategory update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        var categoryDto = serviceCategoryPort.findOneOrThrow(withCategoryId(serviceCategoryId));
        ofNullable(command.getName()).ifPresent(this::throwIfCategoryNameExist);
        ofNullable(command.getServiceIds()).ifPresent(this::throwIfServiceIdsNotExists);
        return buildServiceCategory(categoryDto, command).validate();
    }

    private void throwIfCategoryNameExist(String name) {
        if (serviceCategoryPort.findOne(withName(name)).isPresent()) {
            throw new ServiceCategoryUpdateException(
                    format("Service category already exists by name: %s", name)
            );
        }
    }

    private void throwIfServiceIdsNotExists(Set<String> serviceIds) {
        if (serviceCategoryItemIds.isExists(serviceIds)) {
            throw new ServiceCategoryUpdateException(
                    format("Service ids is not correct: %s", serviceIds)
            );
        }
    }

    public ServiceCategory buildServiceCategory(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ServiceCategory.builder()
                .id(dto.getId())
                .name(getName(dto, command))
                .status(getStatus(dto, command))
                .itemIds(getServiceIds(dto, command))
                .build();
    }

    private Set<String> getServiceIds(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.getServiceIds())
                .filter(not(Set::isEmpty))
                .orElseGet(dto::getItemIds);
    }

    private String getName(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.getName())
                .orElseGet(dto::getName);
    }

    private ServiceCategoryStatus getStatus(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.getStatus())
                .map(ServiceCategoryStatus::valueOf)
                .orElseGet(() -> valueOf(dto.getStatus()));
    }

}
