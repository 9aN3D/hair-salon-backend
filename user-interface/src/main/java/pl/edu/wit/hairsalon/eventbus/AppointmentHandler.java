package pl.edu.wit.hairsalon.eventbus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;

@Service
@RequiredArgsConstructor
class AppointmentHandler implements ReservationMadeEventHandler {

    private final AppointmentFacade appointmentFacade;

    @Override
    public void handle(ReservationMadeEvent event) {
        appointmentFacade.create(buildCommand(event));
    }

    private AppointmentCreateCommand buildCommand(ReservationMadeEvent event) {
        var reservation = event.getReservation();
        return AppointmentCreateCommand.builder()
                .reservationId(reservation.getId())
                .times(reservation.getTimes())
                .hairdresserId(reservation.getHairdresser().getId())
                .memberId(reservation.getMemberId())
                .services(reservation.getSelectedServices())
                .creationDateTime(reservation.getCreationDateTime())
                .build();
    }

}
