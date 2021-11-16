package pl.edu.wit.hairsalon.serviceCategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;
import static pl.edu.wit.hairsalon.serviceCategory.ServiceCategoryStatus.valueOf;
import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withCategoryId;

@RequiredArgsConstructor
class ServiceCategoryUpdater {

    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryCommandHandlers commandHandlers;

    ServiceCategory update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        var categoryDto = serviceCategoryPort.findOneOrThrow(withCategoryId(serviceCategoryId));
        commandHandlers.handle(command);
        return buildServiceCategory(categoryDto, command).validate();
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
