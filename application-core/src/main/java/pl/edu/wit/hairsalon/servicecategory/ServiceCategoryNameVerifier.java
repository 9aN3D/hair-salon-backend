package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.servicecategory.exception.ServiceCategoryUpdateException;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery.withName;

@RequiredArgsConstructor
class ServiceCategoryNameVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryPort serviceCategoryPort;

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfCategoryNameExist(command.getName());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.getName())
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
