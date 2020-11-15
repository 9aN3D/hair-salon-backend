package pl.edu.wit.domain.event;

import pl.edu.wit.domain.DomainEvent;

public interface EventPublisher {

    void publish(DomainEvent event);

}
