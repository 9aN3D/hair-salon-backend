package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.command.ReservationMakeCommand;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.ReservationResponseAdapter;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.ReservationCalculationResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za rezerwowanie wizyt.
 * <p>
 * Wszystkie endpointy wymagają uwierzytelnienia przy pomocy JWT (Bearer token).
 * </p>
 *
 * @see ReservationResponseAdapter
 */
@RestController
@SecurityRequirement(name = "hair-salon-API")
@RequestMapping(value = "/api/v1/reservations")
class ReservationController {

    private final ReservationResponseAdapter reservationAdapter;

    /**
     * Tworzy kontroler rezerwacji.
     *
     * @param reservationAdapter adapter obsługujący logikę rezerwacji
     */
    ReservationController(ReservationResponseAdapter reservationAdapter) {
        this.reservationAdapter = reservationAdapter;
    }

    /**
     * Tworzy nową rezerwację dla zalogowanego użytkownika.
     *
     * @param identity dane uwierzytelnionego użytkownika
     * @param command dane rezerwacji
     */
    @Operation(
            summary = "Utwórz nową rezerwację",
            description = "Tworzy rezerwację wizyty na podstawie przekazanych danych. Wymaga autoryzacji JWT.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Szczegóły rezerwacji",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rezerwacja została pomyślnie utworzona"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void make(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull @RequestBody ReservationMakeCommand command) {
        reservationAdapter.make(identity.id(), command);
    }

    /**
     * Oblicza dostępne usługi, fryzjerów i terminy dla nowej rezerwacji.
     *
     * @param identity dane uwierzytelnionego użytkownika
     * @param command parametry zapytania do kalkulacji rezerwacji
     * @return szczegóły kalkulacji rezerwacji
     */
    @Operation(
            summary = "Kalkulacja dostępnych opcji rezerwacji",
            description = "Zwraca dostępnych fryzjerów, usługi, czasy oraz cenę dla podanych kryteriów. Wymaga autoryzacji JWT.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Parametry do kalkulacji rezerwacji",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono kalkulację dostępnych opcji"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak autoryzacji", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/calculations", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    ReservationCalculationResponse calculate(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull ReservationCalculateCommand command) {
        return reservationAdapter.calculate(identity.id(), command);
    }

}
