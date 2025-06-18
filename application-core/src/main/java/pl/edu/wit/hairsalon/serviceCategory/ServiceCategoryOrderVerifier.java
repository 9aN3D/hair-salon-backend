package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.exception.ServiceCategoryCreateException;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery.withOrder;

class ServiceCategoryOrderVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryPort serviceCategoryPort;

    ServiceCategoryOrderVerifier(ServiceCategoryPort serviceCategoryPort) {
        this.serviceCategoryPort = serviceCategoryPort;
    }

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfOrderAlreadyExists(command.order());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.order())
                .ifPresent(this::throwIfOrderAlreadyExists);
    }

    private void throwIfOrderAlreadyExists(Integer order) {
        if (serviceCategoryPort.findOne(withOrder(order)).isPresent()) {
            throw new ServiceCategoryCreateException(
                    format("Service category already exists by order: %s", order)
            );
        }
    }

}
