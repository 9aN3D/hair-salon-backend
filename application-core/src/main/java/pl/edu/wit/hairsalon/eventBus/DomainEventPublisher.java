package pl.edu.wit.hairsalon.eventBus;

public interface DomainEventPublisher {

    void publishEvent(Object event);

}
