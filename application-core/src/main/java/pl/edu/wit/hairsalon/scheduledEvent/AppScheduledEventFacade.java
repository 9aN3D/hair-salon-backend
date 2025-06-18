package pl.edu.wit.hairsalon.scheduledEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;

import static java.util.Objects.requireNonNull;

class AppScheduledEventFacade implements ScheduledEventFacade {

    private final ScheduledEventPort scheduledEventPort;
    private final ScheduledEventCreator scheduledEventCreator;

    AppScheduledEventFacade(ScheduledEventPort scheduledEventPort, ScheduledEventCreator scheduledEventCreator) {
        this.scheduledEventPort = scheduledEventPort;
        this.scheduledEventCreator = scheduledEventCreator;
    }

    @Override
    public String create(ScheduledEventCreateCommand command) {
        requireNonNull(command, "Scheduled event create command must not be null");
        var savedScheduledEvent = scheduledEventCreator.create(command);
        return savedScheduledEvent.id();
    }

    @Override
    public void delete(String reservationId) {
        requireNonNull(reservationId, "Reservation id must not be null");
        scheduledEventPort.deleteByReservationId(reservationId);
    }

    @Override
    public Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        requireNonNull(findQuery, "Scheduled event find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        return scheduledEventPort.findAll(findQuery, pageable);
    }

    @Override
    public long count(ScheduledEventFindQuery findQuery) {
        requireNonNull(findQuery, "Scheduled event find query must not be null");
        return scheduledEventPort.count(findQuery);
    }
}
