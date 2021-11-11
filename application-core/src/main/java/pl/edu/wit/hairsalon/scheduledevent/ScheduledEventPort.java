package pl.edu.wit.hairsalon.scheduledevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.scheduledevent.dto.ScheduledEventDto;
import pl.edu.wit.hairsalon.scheduledevent.query.ScheduledEventFindQuery;

public interface ScheduledEventPort {

    ScheduledEventDto save(ScheduledEventDto scheduledEvent);

    void deleteByReservationId(String reservationId);

    Page<ScheduledEventDto> findAll(ScheduledEventFindQuery findQuery, Pageable pageable);

    long count(ScheduledEventFindQuery findQuery);

}
