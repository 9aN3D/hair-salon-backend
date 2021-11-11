package pl.edu.wit.hairsalon.servicecategory;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;

import java.util.Set;

@RequiredArgsConstructor
class ServiceCategoryCommandHandlers {

    private final Set<ServiceCategoryCommandVerifier> verifiers;

    void handle(ServiceCategoryCreateCommand command) {
        verifiers.forEach(verifier -> verifier.verify(command));
    }

    void handle(ServiceCategoryUpdateCommand command) {
        verifiers.forEach(verifier -> verifier.verify(command));
    }

}
