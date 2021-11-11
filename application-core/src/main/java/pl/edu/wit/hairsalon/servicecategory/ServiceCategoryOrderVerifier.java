package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.servicecategory.exception.ServiceCategoryCreateException;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery.withOrder;

@RequiredArgsConstructor
class ServiceCategoryOrderVerifier implements ServiceCategoryCommandVerifier {

    private final ServiceCategoryPort serviceCategoryPort;

    @Override
    public void verify(ServiceCategoryCreateCommand command) {
        throwIfOrderAlreadyExists(command.getOrder());
    }

    @Override
    public void verify(ServiceCategoryUpdateCommand command) {
        ofNullable(command.getOrder())
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
