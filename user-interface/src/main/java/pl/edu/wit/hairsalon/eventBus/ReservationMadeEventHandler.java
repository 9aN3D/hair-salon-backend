package pl.edu.wit.hairsalon.eventBus;

import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

/**
 * Interfejs obsługi zdarzenia {@link ReservationMadeEvent}.
 * <p>
 * Implementacje tego interfejsu reagują na utworzenie nowej rezerwacji, wykonując
 * odpowiednie akcje w różnych kontekstach systemu (np. tworzenie spotkania, dodanie do harmonogramu).
 * </p>
 *
 * <p>Stosowany w architekturze zdarzeniowej jako punkt rozszerzania reakcji na rezerwację.</p>
 *
 * @see ReservationMadeEvent
 * @see AppointmentHandler
 * @see ScheduledEventHandler
 */
interface ReservationMadeEventHandler {

    void handle(ReservationMadeEvent event);

}
