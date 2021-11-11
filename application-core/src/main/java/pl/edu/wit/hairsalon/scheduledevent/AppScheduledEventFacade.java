package pl.edu.wit.hairsalon.scheduledevent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.scheduledevent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledevent.query.ScheduledEventFindQuery;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppScheduledEventFacade implements ScheduledEventFacade {

    private final ScheduledEventPort scheduledEventPort;
    private final ScheduledEventCreator scheduledEventCreator;

    @Override
    public String create(ScheduledEventCreateCommand command) {
        log.trace("Creating scheduled event {command: {}}", command);
        requireNonNull(command, "Scheduled event create command must not be null");
        var savedScheduledEvent = scheduledEventCreator.create(command);
        log.info("Created scheduled event {result: {}}", savedScheduledEvent);
        return savedScheduledEvent.getId();
    }

    @Override
    public void delete(String reservationId) {
        log.trace("Deleting scheduled event {reservationId: {}}", reservationId);
        requireNonNull(reservationId, "Reservation id must not be null");
        scheduledEventPort.deleteByReservationId(reservationId);
        log.info("Deleted scheduled event {reservationId: {}}", reservationId);
    }

    @Override
    public Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        log.trace("Searching scheduled events {findQuery: {}, pageable: {}}", findQuery, pageable);
        requireNonNull(findQuery, "Scheduled event find query must not be null");
        requireNonNull(pageable, "Pageable must not be null");
        var page = scheduledEventPort.findAll(findQuery, pageable);
        log.info("Searched scheduled events {numberOfElements: {}}", page.getNumberOfElements());
        return page;
    }

    @Override
    public long count(ScheduledEventFindQuery findQuery) {
        log.trace("Counting scheduled events {findQuery: {}}", findQuery);
        requireNonNull(findQuery, "scheduled event find query must not be null");
        var scheduledEventCount = scheduledEventPort.count(findQuery);
        log.info("Counted scheduled events {result: {}}", scheduledEventCount);
        return scheduledEventCount;
    }

}
