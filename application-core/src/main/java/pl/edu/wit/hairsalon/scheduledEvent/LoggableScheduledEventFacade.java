package pl.edu.wit.hairsalon.scheduledEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;

import java.util.List;

class LoggableScheduledEventFacade implements ScheduledEventFacade {

    private final Logger log;
    private final ScheduledEventFacade delegate;

    LoggableScheduledEventFacade(ScheduledEventFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableScheduledEventFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String create(ScheduledEventCreateCommand command) {
        log.trace("Creating scheduled event {command: {}}", command);
        var result = delegate.create(command);
        log.info("Created scheduled event {result: {}}", result);
        return result;
    }

    @Override
    public void delete(String reservationId) {
        log.trace("Deleting scheduled event {reservationId: {}}", reservationId);
        delegate.delete(reservationId);
        log.info("Deleted scheduled event {reservationId: {}}", reservationId);
    }

    @Override
    public List<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery) {
        log.trace("Searching scheduled events {findQuery: {}}", findQuery);
        var result = delegate.findAll(findQuery);
        log.info("Searched scheduled events {size: {}}", result.size());
        return result;
    }

    @Override
    public Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        log.trace("Searching scheduled events {findQuery: {}, pageable: {}}", findQuery, pageable);
        var result = delegate.findAll(findQuery, pageable);
        log.info("Searched scheduled events {numberOfElements: {}}", result.getNumberOfElements());
        return result;
    }

    @Override
    public long count(ScheduledEventFindQuery findQuery) {
        log.trace("Counting scheduled events {findQuery: {}}", findQuery);
        var result = delegate.count(findQuery);
        log.info("Counted scheduled events {result: {}}", result);
        return result;
    }

}
