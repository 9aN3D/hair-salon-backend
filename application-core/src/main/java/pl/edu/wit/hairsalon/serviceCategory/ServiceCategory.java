package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.sharedKernel.CollectionHelper.isNullOrEmpty;

record ServiceCategory(
        String id,
        String name,
        Integer order,
        ServiceCategoryStatus status,
        Set<String> itemIds
) implements SelfValidator<ServiceCategory> {

    @Override
    public ServiceCategory validate() {
        validate(new NotBlankString(id), new NotBlankString(name));
        requireNonNull(status, "Service category status must not be null");
        requireNonNull(order, "Service category order must not be null");
        if (isNullOrEmpty(itemIds)) {
            throw new ValidationException("Category item ids must not be null or empty");
        }
        return this;
    }

    ServiceCategoryDto toDto() {
        return ServiceCategoryDto.builder()
                .id(id)
                .name(name)
                .order(order)
                .status(status.toDto())
                .itemIds(itemIds)
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private String name;
        private Integer order;
        private ServiceCategoryStatus status;
        private Set<String> itemIds;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder order(Integer order) {
            this.order = order;
            return this;
        }

        Builder status(ServiceCategoryStatus status) {
            this.status = status;
            return this;
        }

        Builder itemIds(Set<String> itemIds) {
            this.itemIds = itemIds;
            return this;
        }

        ServiceCategory build() {
            return new ServiceCategory(id, name, order, status, itemIds);
        }

    }

}
