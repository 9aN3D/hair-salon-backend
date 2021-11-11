package pl.edu.wit.hairsalon.eventbus;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
class DefaultDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(Object event) {
        applicationEventPublisher.publishEvent(event);
    }

}
