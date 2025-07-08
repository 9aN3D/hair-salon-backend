package pl.edu.wit.hairsalon.eventBus;

import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

/**
 * Obsługuje zdarzenie {@link ReservationMadeEvent}, tworząc odpowiedni {@code Appointment}.
 * <p>
 * Klasa działa jako adapter pomiędzy warstwą zdarzeń domenowych a logiką aplikacyjną zawartą w {@link AppointmentFacade}.
 * Po otrzymaniu zdarzenia rezerwacji tworzy komendę {@link AppointmentCreateCommand}
 * i deleguje proces tworzenia spotkania do fasady.
 * </p>
 *
 */
@Service
class AppointmentHandler implements ReservationMadeEventHandler {

    private final AppointmentFacade appointmentFacade;

    /**
     * Tworzy handler spotkań.
     *
     * @param appointmentFacade fasada odpowiedzialna za logikę tworzenia spotkań
     */
    public AppointmentHandler(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    /**
     * Obsługuje zdarzenie {@link ReservationMadeEvent}, inicjując proces tworzenia spotkania.
     *
     * @param event zdarzenie rezerwacji dokonanej przez użytkownika
     */
    @Override
    public void handle(ReservationMadeEvent event) {
        appointmentFacade.create(buildCommand(event));
    }

    /**
     * Buduje komendę {@link AppointmentCreateCommand} na podstawie danych z rezerwacji.
     *
     * @param event zdarzenie rezerwacji
     * @return komenda tworzenia spotkania
     */
    private AppointmentCreateCommand buildCommand(ReservationMadeEvent event) {
        var reservation = event.reservation();
        return AppointmentCreateCommand.builder()
                .reservationId(reservation.id())
                .times(reservation.times())
                .hairdresserId(reservation.hairdresser().id())
                .memberId(reservation.memberId())
                .services(reservation.selectedServices())
                .creationDateTime(reservation.creationDateTime())
                .build();
    }

}
