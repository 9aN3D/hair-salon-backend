package pl.edu.wit.hairsalon.eventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

import java.util.Set;

/**
 * Słuchacz zdarzeń {@link ReservationMadeEvent}, odpowiedzialny za delegowanie obsługi
 * do wszystkich zarejestrowanych {@link ReservationMadeEventHandler}.
 * <p>
 * Klasa ta centralizuje reakcję systemu na nowe rezerwacje, umożliwiając jednoczesne
 * wywołanie wielu niezależnych komponentów (np. tworzenie spotkania, aktualizacja kalendarza).
 * </p>
 *
 * <p>Każdy komponent obsługujący zdarzenie implementuje {@link ReservationMadeEventHandler}
 * i zostaje wstrzyknięty w postaci zestawu.</p>
 *
 * @see ReservationMadeEvent
 * @see ReservationMadeEventHandler
 */
@Service
class ReservationMadeEventListener {

    private final Logger log;
    private final Set<ReservationMadeEventHandler> handlers;

    /**
     * Tworzy słuchacza zdarzenia {@link ReservationMadeEvent}.
     *
     * @param handlers zbiór handlerów obsługujących zdarzenie rezerwacji
     */
    public ReservationMadeEventListener(Set<ReservationMadeEventHandler> handlers) {
        this.log = LoggerFactory.getLogger(ReservationMadeEventListener.class);
        this.handlers = handlers;
    }

    /**
     * Reaguje na wystąpienie zdarzenia {@link ReservationMadeEvent} i deleguje jego obsługę
     * do wszystkich zarejestrowanych handlerów.
     *
     * @param event zdarzenie rezerwacji
     */
    @DomainEventListener
    @SuppressWarnings("unused")
    public void onReservationMadeEvent(ReservationMadeEvent event) {
        log.trace("Receiving reservation made event {event: {}}", event);
        handlers.forEach(handler -> handler.handle(event));
        log.info("Received reservation made event {event: {}}", event);
    }

}
