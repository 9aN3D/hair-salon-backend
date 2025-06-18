package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.exception.ServiceCategoryCreateException;

import java.util.Set;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

class ServiceCategoryServiceIdsVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryItemIds serviceCategoryItemIds;

    ServiceCategoryServiceIdsVerifier(ServiceCategoryItemIds serviceCategoryItemIds) {
        this.serviceCategoryItemIds = serviceCategoryItemIds;
    }

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfServiceIdsNotExists(command.serviceIds());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.serviceIds())
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
