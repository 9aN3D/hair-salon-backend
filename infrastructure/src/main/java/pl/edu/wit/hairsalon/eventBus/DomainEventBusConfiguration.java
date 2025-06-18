package pl.edu.wit.hairsalon.eventBus;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class DomainEventBusConfiguration {

    @Bean
    DomainEventPublisher domainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultDomainEventPublisher(applicationEventPublisher);
    }

}
