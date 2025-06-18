package pl.edu.wit.hairsalon.eventBus;

import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.reservation.event.ReservationMadeEvent;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;

import static pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventTypeDto.RESERVED_SERVICE;

@Service
class ScheduledEventHandler implements ReservationMadeEventHandler {

    private final ScheduledEventFacade scheduledEventFacade;

    public ScheduledEventHandler(ScheduledEventFacade scheduledEventFacade) {
        this.scheduledEventFacade = scheduledEventFacade;
    }

    @Override
    public void handle(ReservationMadeEvent event) {
        scheduledEventFacade.create(buildCreateCommand(event));
    }

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
