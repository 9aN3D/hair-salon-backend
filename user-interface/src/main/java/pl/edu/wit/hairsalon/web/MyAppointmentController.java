package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.AppointmentResponseAdapter;
import pl.edu.wit.hairsalon.web.response.AppointmentConciseResponse;
import pl.edu.wit.hairsalon.web.response.AppointmentResponse;
import pl.edu.wit.hairsalon.web.response.LinkAddingGoogleCalendarEventResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST umożliwiający zalogowanemu klientowi przeglądanie i zarządzanie swoimi wizytami.
 * <p>
 * Wszystkie endpointy wymagają uwierzytelnienia JWT (Bearer token w nagłówku {@code Authorization}).
 * </p>
 *
 * @see AppointmentResponseAdapter
 */
@RestController
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/me/appointments")
class MyAppointmentController {

    private final AppointmentResponseAdapter appointmentResponseAdapter;

    /**
     * Tworzy kontroler do obsługi wizyt użytkownika.
     *
     * @param appointmentResponseAdapter adapter odpowiedzialny za logikę operacji na wizytach
     */
    MyAppointmentController(AppointmentResponseAdapter appointmentResponseAdapter) {
        this.appointmentResponseAdapter = appointmentResponseAdapter;
    }

    /**
     * Zwraca listę skróconych informacji o wizytach zalogowanego klienta.
     *
     * @param identity   tożsamość klienta z tokena JWT
     * @param findQuery  kryteria filtrowania
     * @param pageable   paginacja wyników
     * @return lista wizyt
     */
    @Operation(
            summary = "Lista wizyt klienta",
            description = "Zwraca paginowaną listę wizyt zalogowanego użytkownika.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Poprawnie zwrócono wizyty"),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji JWT")
            }
    )
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<AppointmentConciseResponse> findAll(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull AppointmentFindQuery findQuery, Pageable pageable) {
        return appointmentResponseAdapter.findAll(identity.id(), findQuery, pageable);
    }

    /**
     * Zwraca szczegóły jednej wizyty klienta.
     *
     * @param identity      tożsamość klienta
     * @param appointmentId identyfikator wizyty
     * @return szczegóły wizyty
     */
    @Operation(
            summary = "Szczegóły wizyty klienta",
            description = "Zwraca szczegółowe informacje o konkretnej wizycie klienta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Poprawnie zwrócono szczegóły wizyty"),
                    @ApiResponse(responseCode = "404", description = "Wizyta nie została znaleziona lub nie należy do klienta", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/{appointmentId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    AppointmentResponse findOne(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        return appointmentResponseAdapter.findOne(identity.id(), appointmentId);
    }

    /**
     * Rezygnuje z wizyty.
     *
     * @param identity      tożsamość klienta
     * @param appointmentId identyfikator wizyty
     */
    @Operation(
            summary = "Rezygnacja z wizyty",
            description = "Umożliwia klientowi rezygnację z umówionej wizyty.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Rezygnacja przebiegła pomyślnie"),
                    @ApiResponse(responseCode = "404", description = "Wizyta nie została znaleziona lub nie należy do klienta", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PutMapping(value = "/{appointmentId}/resign")
    @ResponseStatus(NO_CONTENT)
    void resign(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        appointmentResponseAdapter.resign(identity.id(), appointmentId);
    }

    /**
     * Zwraca link do dodania wizyty do kalendarza Google.
     *
     * @param identity      tożsamość klienta
     * @param appointmentId identyfikator wizyty
     * @return link do wydarzenia Google Calendar
     */
    @Operation(
            summary = "Link do Google Calendar",
            description = "Zwraca link umożliwiający dodanie wizyty do kalendarza Google klienta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Link został wygenerowany"),
                    @ApiResponse(responseCode = "404", description = "Wizyta nie została znaleziona", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/{appointmentId}/GOOGLE/calendar/event/links", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    LinkAddingGoogleCalendarEventResponse getLinkAddingGoogleCalendarEvent(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @PathVariable String appointmentId) {
        return appointmentResponseAdapter.getLinkAddingGoogleCalendarEvent(identity.id(), appointmentId);
    }

}
