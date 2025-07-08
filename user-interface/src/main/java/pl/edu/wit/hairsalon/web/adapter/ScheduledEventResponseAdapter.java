package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.ScheduledEventResponse;

/**
 * Adapter odpowiedzialny za udostępnianie danych zaplanowanych wydarzeń w kalendarzu.
 * <p>
 * Łączy się z warstwą domenową poprzez {@link ScheduledEventFacade} i przekształca dane domenowe
 * na obiekty typu {@link ScheduledEventResponse}.
 * </p>
 *
 * <p>Aktualnie obsługuje jedynie paginowane wyszukiwanie zaplanowanych wydarzeń.</p>
 *
 * @see ScheduledEventResponse
 * @see ScheduledEventFacade
 */

@Service
public class ScheduledEventResponseAdapter {

    private final ScheduledEventFacade scheduledEventFacade;

    /**
     * Tworzy instancję adaptera z wstrzykniętą fasadą zaplanowanych wydarzeń.
     *
     * @param scheduledEventFacade fasada domenowa do zarządzania zaplanowanymi wydarzeniami
     */
    public ScheduledEventResponseAdapter(ScheduledEventFacade scheduledEventFacade) {
        this.scheduledEventFacade = scheduledEventFacade;
    }

    /**
     * Zwraca zaplanowane wydarzenia pasujące do zapytania, w formie stronicowanej.
     *
     * @param findQuery zapytanie filtrujące 
     * @param pageable  dane o stronicowaniu (strona, rozmiar, sortowanie)
     * @return stronicowana lista wydarzeń
     */
    public PagedResponse<ScheduledEventResponse> findAll(ScheduledEventFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                scheduledEventFacade.findAll(findQuery, pageable)
                        .map(ScheduledEventResponse::of)
        );
    }

}
