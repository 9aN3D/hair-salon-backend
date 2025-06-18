package pl.edu.wit.hairsalon.serviceCategory;

import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;

import java.util.Set;

class ServiceCategoryCommandHandlers {

    private final Set<ServiceCategoryCommandVerifier> verifiers;

    ServiceCategoryCommandHandlers(Set<ServiceCategoryCommandVerifier> verifiers) {
        this.verifiers = verifiers;
    }

    void handle(ServiceCategoryCreateCommand command) {
        verifiers.forEach(verifier -> verifier.verify(command));
    }

    void handle(ServiceCategoryUpdateCommand command) {
        verifiers.forEach(verifier -> verifier.verify(command));
    }

}
