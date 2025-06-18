package pl.edu.wit.hairsalon.scheduledEvent;

import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

class ScheduledEventCreator {

    private final IdGenerator idGenerator;
    private final ScheduledEventPort port;

    ScheduledEventCreator(IdGenerator idGenerator, ScheduledEventPort port) {
        this.idGenerator = idGenerator;
        this.port = port;
    }

    ScheduledEventDto create(ScheduledEventCreateCommand command) {
        var newScheduledEvent = createNewScheduledEvent(command)
                .validate();
        return port.save(newScheduledEvent.toDto());
    }

    private ScheduledEvent createNewScheduledEvent(ScheduledEventCreateCommand command) {
        return ScheduledEvent.builder()
                .id(idGenerator.generate())
                .times(new DateRange(command.times()))
                .type(ScheduledEventType.valueOf(command.type().name()))
                .hairdresserId(command.hairdresserId())
                .reservationId(command.reservationId())
                .build();
    }

}
