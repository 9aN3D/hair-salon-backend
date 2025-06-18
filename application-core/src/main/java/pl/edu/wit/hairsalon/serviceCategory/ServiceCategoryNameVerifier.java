package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.exception.ServiceCategoryUpdateException;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withName;

class ServiceCategoryNameVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryPort serviceCategoryPort;

    ServiceCategoryNameVerifier(ServiceCategoryPort serviceCategoryPort) {
        this.serviceCategoryPort = serviceCategoryPort;
    }

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfCategoryNameExist(command.name());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.name())
                .ifPresent(this::throwIfCategoryNameExist);
    }

    private void throwIfCategoryNameExist(String name) {
        if (serviceCategoryPort.findOne(withName(name)).isPresent()) {
            throw new ServiceCategoryUpdateException(
                    format("Service category already exists by name: %s", name)
            );
        }
    }

}
