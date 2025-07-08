package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.scheduledEvent.query.ScheduledEventFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ScheduledEventResponseAdapter;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.ScheduledEventResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST do obsługi zaplanowanych wydarzeń fryzjerskich.
 * <p>
 * Umożliwia pobieranie zaplanowanych wydarzeń w sposób stronicowany.
 * </p>
 *
 * @see ScheduledEventResponseAdapter
 */
@RestController
@RequestMapping("/api/v1/scheduled-events")
class ScheduledEventController {

    private final ScheduledEventResponseAdapter scheduledEventResponseAdapter;

    /**
     * Tworzy kontroler zaplanowanych wydarzeń.
     *
     * @param scheduledEventResponseAdapter adapter obsługujący logikę zapytań o zaplanowane wydarzenia
     */
    ScheduledEventController(ScheduledEventResponseAdapter scheduledEventResponseAdapter) {
        this.scheduledEventResponseAdapter = scheduledEventResponseAdapter;
    }

    /**
     * Zwraca stronicowaną listę zaplanowanych wydarzeń zgodnie z parametrami filtrowania.
     *
     * @param findQuery zapytanie filtrowania wydarzeń
     * @param pageable  parametry stronicowania
     * @return stronicowana lista zaplanowanych wydarzeń
     */
    @Operation(
            summary = "Pobierz zaplanowane wydarzenia",
            description = "Zwraca zaplanowane wydarzenia na podstawie zapytania filtrowania i stronicowania.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista wydarzeń została zwrócona"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe parametry zapytania", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<ScheduledEventResponse> getAll(@NotNull ScheduledEventFindQuery findQuery, Pageable pageable) {
        return scheduledEventResponseAdapter.findAll(findQuery, pageable);
    }

}
