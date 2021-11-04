package pl.edu.wit.hairsalon.servicecategory;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.sharedkernel.CollectionHelper.isNullOrEmpty;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class ServiceCategory implements SelfValidator<ServiceCategory> {

    private final String id;
    private final String name;
    private final ServiceCategoryStatus status;
    private final Set<String> itemIds;

    @Override
    public ServiceCategory validate() {
        validate(new NotBlankString(id), new NotBlankString(name));
        requireNonNull(status, "Service category status must not be null");
        if (isNullOrEmpty(itemIds)) {
            throw new ValidationException("Category item ids must not be null or empty");
        }
        return this;
    }

    public ServiceCategoryDto toDto() {
        return ServiceCategoryDto.builder()
                .id(id)
                .name(name)
                .status(status.toDto())
                .itemIds(itemIds)
                .build();
    }

}
