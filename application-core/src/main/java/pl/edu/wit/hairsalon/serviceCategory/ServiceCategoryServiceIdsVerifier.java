package pl.edu.wit.hairsalon.serviceCategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.exception.ServiceCategoryCreateException;

import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
class ServiceCategoryServiceIdsVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryItemIds serviceCategoryItemIds;

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfServiceIdsNotExists(command.getServiceIds());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.getServiceIds())
                .ifPresent(this::throwIfServiceIdsNotExists);
    }

    private void throwIfServiceIdsNotExists(Set<String> serviceIds) {
        if (serviceCategoryItemIds.isExists(serviceIds)) {
            throw new ServiceCategoryCreateException(
                    format("Service ids is not correct: %s", serviceIds)
            );
        }
    }

}
