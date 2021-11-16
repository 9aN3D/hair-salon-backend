package pl.edu.wit.hairsalon.scheduledEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.scheduledEvent.command.ScheduledEventCreateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;

public interface ScheduledEventFacade {

    String create(ScheduledEventCreateCommand command);

    void delete(String reservationId);

    Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable);

    long count(ScheduledEventFindQuery findQuery);

}
