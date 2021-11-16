package pl.edu.wit.hairsalon.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ScheduledEventResponseAdapter;
import pl.edu.wit.hairsalon.web.response.ScheduledEventResponse;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scheduled-events")
class ScheduledEventController {

    private final ScheduledEventResponseAdapter scheduledEventResponseAdapter;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Page<ScheduledEventResponse> getAll(@NotNull ScheduledEventFindQuery findQuery, Pageable pageable) {
        return scheduledEventResponseAdapter.findAll(findQuery, pageable);
    }

}
