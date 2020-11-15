package pl.edu.wit.domain.event;

public interface EventPublisher {

    void publish(DomainEvent event);

}
