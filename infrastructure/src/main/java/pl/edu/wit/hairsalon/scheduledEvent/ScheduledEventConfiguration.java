package pl.edu.wit.hairsalon.scheduledEvent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@Configuration(proxyBeanMethods = false)
class ScheduledEventConfiguration {

    @Bean
    ScheduledEventFacade scheduledEventFacade(IdGenerator idGenerator,
                                              ScheduledEventPort scheduledEventPort) {
        var creator = new ScheduledEventCreator(idGenerator, scheduledEventPort);
        return new LoggableScheduledEventFacade(
                new AppScheduledEventFacade(scheduledEventPort, creator)
        );
    }

}
