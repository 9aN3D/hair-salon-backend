package pl.edu.wit.hairsalon.eventbus;

public interface DomainEventPublisher {

    void publishEvent(Object event);

}
