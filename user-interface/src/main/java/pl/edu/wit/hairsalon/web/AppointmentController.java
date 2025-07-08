package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST dla operacji administracyjnych związanych z wizytami.
 * <p>
 * Umożliwia administratorowi wyzwolenie przypomnienia o nadchodzących wizytach.
 * Endpoint zabezpieczony mechanizmem autoryzacji zdefiniowanym przez {@code hair-salon-API},
 * opartym na tokenach JWT w schemacie Bearer przekazywanych w nagłówku {@code Authorization}
 * </p>
 *
 * <p>Ścieżka bazowa: {@code /api/v1/admin/appointments}</p>
 *
 * @see AppointmentFacade
 */
@RestController
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/admin/appointments")
class AppointmentController {

    private final AppointmentFacade appointmentFacade;

    /**
     * Tworzy instancję kontrolera wizyt administracyjnych.
     *
     * @param appointmentFacade fasada odpowiedzialna za logikę biznesową wizyt
     */
    AppointmentController(AppointmentFacade appointmentFacade) {
        this.appointmentFacade = appointmentFacade;
    }

    /**
     * Wyzwala przypomnienia o nadchodzących wizytach dla klientów.
     * <p>
     * Operacja może być wykonywana ręcznie przez administratora.
     * Używa paginacji, aby obsłużyć dużą liczbę wizyt.
     * </p>
     *
     * @param pageable obiekt określający stronę, rozmiar i sortowanie
     */
    @Operation(
            summary = "Wyślij przypomnienia o nadchodzących wizytach",
            description = "Endpoint wyzwala mechanizm przypominający klientom o zaplanowanych wizytach. Wymaga autoryzacji jako administrator.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Przypomnienia zostały przetworzone pomyślnie"),
                    @ApiResponse(responseCode = "401", description = "Brak uwierzytelnienia", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Brak uprawnień", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/reminds")
    void remindAppointments(@NotNull Pageable pageable) {
        appointmentFacade.reminds(pageable);
    }

}
