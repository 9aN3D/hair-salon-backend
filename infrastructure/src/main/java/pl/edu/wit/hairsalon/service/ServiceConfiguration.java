package pl.edu.wit.hairsalon.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@Configuration(proxyBeanMethods = false)
class ServiceConfiguration {

    @Bean
    ServiceFacade serviceFacade(ServicePort servicePort,
                                IdGenerator idGenerator) {
        var creator = new ServiceCreator(servicePort, idGenerator);
        var updater = new ServiceUpdater(servicePort);
        return new LoggableServiceFacade(
                new AppServiceFacade(servicePort, creator, updater)
        );
    }

}
