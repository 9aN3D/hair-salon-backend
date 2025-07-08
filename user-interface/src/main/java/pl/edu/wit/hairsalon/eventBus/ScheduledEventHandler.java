package pl.edu.wit.hairsalon.eventBus;

import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;

import static pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto.RESERVED_SERVICE;

/**
 * Obsługuje zdarzenie {@link ReservationMadeEvent}, tworząc {@code ScheduledEvent}
 * reprezentujący zaplanowaną usługę w harmonogramie fryzjera.
 * <p>
 * Handler ten działa jako pomost między warstwą zdarzeń domenowych a fasadą {@link ScheduledEventFacade}.
 * Tworzy obiekt {@link ScheduledEventCreateCommand} i przekazuje go do fasady w celu utworzenia wpisu w harmonogramie.
 * </p>
 */
@Service
class ScheduledEventHandler implements ReservationMadeEventHandler {

    private final ScheduledEventFacade scheduledEventFacade;

    /**
     * Tworzy handler zaplanowanych wydarzeń.
     *
     * @param scheduledEventFacade fasada odpowiedzialna za tworzenie wydarzeń w harmonogramie
     */
    public ScheduledEventHandler(ScheduledEventFacade scheduledEventFacade) {
        this.scheduledEventFacade = scheduledEventFacade;
    }

    /**
     * Obsługuje zdarzenie {@link ReservationMadeEvent}, tworząc zaplanowaną usługę w kalendarzu
     *
     * @param event zdarzenie rezerwacji dokonanej przez użytkownika
     */
    @Override
    public void handle(ReservationMadeEvent event) {
        scheduledEventFacade.create(buildCreateCommand(event));
    }

    /**
     * Buduje komendę {@link ScheduledEventCreateCommand} na podstawie danych z rezerwacji.
     * Ustawia typ zdarzenia jako {@code RESERVED_SERVICE}.
     *
     * @param event zdarzenie rezerwacji
     * @return komenda tworzenia zdarzenia w harmonogramie
     */
    private ScheduledEventCreateCommand buildCreateCommand(ReservationMadeEvent event) {
        var reservation = event.reservation();
        return ScheduledEventCreateCommand.builder()
                .reservationId(reservation.id())
                .hairdresserId(reservation.hairdresser().id())
                .times(reservation.times())
                .type(RESERVED_SERVICE)
                .build();
    }

}
