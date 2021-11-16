package pl.edu.wit.hairsalon.eventBus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationMadeEventListener {

    private final Set<ReservationMadeEventHandler> handlers;

    @DomainEventListener
    @SuppressWarnings("unused")
    public void onReservationMadeEvent(ReservationMadeEvent event) {
        log.trace("Receiving reservation made event {event: {}}", event);
        handlers.forEach(handler -> handler.handle(event));
        log.info("Received reservation made event {event: {}}", event);
    }

}
