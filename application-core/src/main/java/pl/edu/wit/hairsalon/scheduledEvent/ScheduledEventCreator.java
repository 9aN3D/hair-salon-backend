package pl.edu.wit.hairsalon.scheduledEvent;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

@RequiredArgsConstructor
class ScheduledEventCreator {

    private final IdGenerator idGenerator;
    private final ScheduledEventPort port;

    ScheduledEventDto create(ScheduledEventCreateCommand command) {
        var newScheduledEvent = createNewScheduledEvent(command)
                .validate();
        return port.save(newScheduledEvent.toDto());
    }

    private ScheduledEvent createNewScheduledEvent(ScheduledEventCreateCommand command) {
        return ScheduledEvent.builder()
                .id(idGenerator.generate())
                .times(new DateRange(command.getTimes()))
                .type(ScheduledEventType.valueOf(command.getType().name()))
                .hairdresserId(command.getHairdresserId())
                .reservationId(command.getReservationId())
                .build();
    }

}
