package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;
import static pl.edu.wit.hairsalon.serviceCategory.ServiceCategoryStatus.valueOf;
import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withCategoryId;

class ServiceCategoryUpdater {

    private final ServiceCategoryPort serviceCategoryPort;
    private final ServiceCategoryCommandHandlers commandHandlers;

    ServiceCategoryUpdater(ServiceCategoryPort serviceCategoryPort, ServiceCategoryCommandHandlers commandHandlers) {
        this.serviceCategoryPort = serviceCategoryPort;
        this.commandHandlers = commandHandlers;
    }

    ServiceCategory update(String serviceCategoryId, ServiceCategoryUpdateCommand command) {
        var categoryDto = serviceCategoryPort.findOneOrThrow(withCategoryId(serviceCategoryId));
        commandHandlers.handle(command);
        return buildServiceCategory(categoryDto, command).validate();
    }

    private ServiceCategory buildServiceCategory(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ServiceCategory.builder()
                .id(dto.id())
                .name(getName(dto, command))
                .status(getStatus(dto, command))
                .itemIds(getServiceIds(dto, command))
                .build();
    }

    private Set<String> getServiceIds(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.serviceIds())
                .filter(not(Set::isEmpty))
                .orElseGet(dto::itemIds);
    }

    private String getName(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.name())
                .orElseGet(dto::name);
    }

    private ServiceCategoryStatus getStatus(ServiceCategoryDto dto, ServiceCategoryUpdateCommand command) {
        return ofNullable(command.status())
                .map(ServiceCategoryStatus::valueOf)
                .orElseGet(() -> valueOf(dto.status()));
    }

}
