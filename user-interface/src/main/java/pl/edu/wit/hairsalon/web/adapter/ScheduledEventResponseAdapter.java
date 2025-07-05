package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.ScheduledEventResponse;

@Service
public class ScheduledEventResponseAdapter {

    private final ScheduledEventFacade scheduledEventFacade;

    public ScheduledEventResponseAdapter(ScheduledEventFacade scheduledEventFacade) {
        this.scheduledEventFacade = scheduledEventFacade;
    }

    public PagedResponse<ScheduledEventResponse> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                scheduledEventFacade.findAll(findQuery, pageable)
                        .map(ScheduledEventResponse::of)
        );
    }

}
