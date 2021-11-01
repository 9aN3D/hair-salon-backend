package pl.edu.wit.hairsalon.servicecategory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class ServiceCategoryConfiguration {

    @Bean
    ServiceCategoryFacade serviceCategoryFacade(ServiceCategoryPort serviceCategoryPort,
                                                IdGenerator idGenerator,
                                                ServiceFacade serviceFacade) {
        var itemIds = new ServiceCategoryItemIds(serviceFacade);
        var creator = new ServiceCategoryCreator(idGenerator, serviceCategoryPort, itemIds);
        var updater = new ServiceCategoryUpdater(serviceCategoryPort, itemIds);
        return new AppServiceCategoryFacade(serviceCategoryPort, creator, updater);
    }

}
