package pl.edu.wit.hairsalon.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

@Configuration
class ServiceConfiguration {

    @Bean
    ServiceFacade serviceFacade(ServicePort servicePort,
                                IdGenerator idGenerator) {
        var creator = new ServiceCreator(servicePort, idGenerator);
        var updater = new ServiceUpdater(servicePort);
        return new AppServiceFacade(servicePort, creator, updater);
    }

}
