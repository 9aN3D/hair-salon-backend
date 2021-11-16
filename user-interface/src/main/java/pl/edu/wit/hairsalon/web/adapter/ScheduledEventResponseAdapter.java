package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.web.response.ScheduledEventResponse;

@Service
@RequiredArgsConstructor
public class ScheduledEventResponseAdapter {

    private final ScheduledEventFacade scheduledEventFacade;

    public Page<ScheduledEventResponse> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        return scheduledEventFacade.findAll(findQuery, pageable)
                .map(ScheduledEventResponse::of);
    }

}
