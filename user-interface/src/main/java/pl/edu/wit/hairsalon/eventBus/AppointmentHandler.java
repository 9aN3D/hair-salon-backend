package pl.edu.wit.hairsalon.eventBus;

import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

@Service
class AppointmentHandler implements ReservationMadeEventHandler {

    private final AppointmentFacade appointmentFacade;

    public AppointmentHandler(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    @Override
    public void handle(ReservationMadeEvent event) {
        appointmentFacade.create(buildCommand(event));
    }

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
