package pl.edu.wit.hairsalon.serviceCategory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.util.Set;

@Configuration(proxyBeanMethods = false)
class ServiceCategoryConfiguration {

    @Bean
    ServiceCategoryFacade serviceCategoryFacade(ServiceCategoryPort serviceCategoryPort,
                                                IdGenerator idGenerator,
                                                Set<ServiceCategoryCommandVerifier> commandVerifiers) {
        var commandHandlers = new ServiceCategoryCommandHandlers(commandVerifiers);
        var creator = new ServiceCategoryCreator(idGenerator, commandHandlers);
        var updater = new ServiceCategoryUpdater(serviceCategoryPort, commandHandlers);
        return new LoggableServiceCategoryFacade(
                new AppServiceCategoryFacade(serviceCategoryPort, creator, updater)
        );
    }

    @Bean
    ServiceCategoryCommandVerifier serviceCategoryNameVerifier(ServiceCategoryPort serviceCategoryPort) {
        return new ServiceCategoryNameVerifier(serviceCategoryPort);
    }

    @Bean
    ServiceCategoryCommandVerifier serviceCategoryServiceIdsVerifier(ServiceFacade serviceFacade) {
        var itemIds = new ServiceCategoryItemIds(serviceFacade);
        return new ServiceCategoryServiceIdsVerifier(itemIds);
    }

    @Bean
    ServiceCategoryCommandVerifier serviceCategoryOrderVerifier(ServiceCategoryPort serviceCategoryPort) {
        return new ServiceCategoryOrderVerifier(serviceCategoryPort);
    }

}
