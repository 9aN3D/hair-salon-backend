package pl.edu.wit.hairsalon.eventBus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;

import static pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto.RESERVED_SERVICE;

@Service
@RequiredArgsConstructor
class ScheduledEventHandler implements ReservationMadeEventHandler {

    private final ScheduledEventFacade scheduledEventFacade;

    @Override
    public void handle(ReservationMadeEvent event) {
        scheduledEventFacade.create(buildCreateCommand(event));
    }

    private ScheduledEventCreateCommand buildCreateCommand(ReservationMadeEvent event) {
        var reservation = event.getReservation();
        return ScheduledEventCreateCommand.builder()
                .reservationId(reservation.getId())
                .hairdresserId(reservation.getHairdresser().getId())
                .times(reservation.getTimes())
                .type(RESERVED_SERVICE)
                .build();
    }

}
