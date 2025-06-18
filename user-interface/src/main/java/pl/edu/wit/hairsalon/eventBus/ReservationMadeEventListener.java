package pl.edu.wit.hairsalon.eventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

import java.util.Set;

@Service
class ReservationMadeEventListener {

    private final Logger log;
    private final Set<ReservationMadeEventHandler> handlers;

    public ReservationMadeEventListener(Set<ReservationMadeEventHandler> handlers) {
        this.log = LoggerFactory.getLogger(ReservationMadeEventListener.class);
        this.handlers = handlers;
    }

    @DomainEventListener
    @SuppressWarnings("unused")
    public void onReservationMadeEvent(ReservationMadeEvent event) {
        log.trace("Receiving reservation made event {event: {}}", event);
        handlers.forEach(handler -> handler.handle(event));
        log.info("Received reservation made event {event: {}}", event);
    }

}
