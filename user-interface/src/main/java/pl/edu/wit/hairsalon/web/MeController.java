package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.authDetails.AuthDetailsFacade;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST zwracający dane aktualnie uwierzytelnionego użytkownika (klienta).
 * <p>
 * Endpoint wymaga autoryzacji JWT w schemacie Bearer przekazywanym w nagłówku
 * {@code Authorization}, zgodnie z definicją {@code hair-salon-API}.
 * </p>
 *
 * @see AuthDetailsFacade
 */
@RestController
@RequestMapping(value = "/api/v1/me", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class MeController {

    private final AuthDetailsFacade authDetailsFacade;

    /**
     * Tworzy kontroler do obsługi zapytań o dane zalogowanego użytkownika.
     *
     * @param authDetailsFacade fasada dostępu do danych użytkownika
     */
    MeController(AuthDetailsFacade authDetailsFacade) {
        this.authDetailsFacade = authDetailsFacade;
    }

    /**
     * Zwraca szczegóły uwierzytelnionego użytkownika (klienta).
     *
     * @param identity kontekst uwierzytelnionego użytkownika przekazany przez Spring Security
     * @return dane użytkownika
     */
    @Operation(
            summary = "Pobierz dane zalogowanego użytkownika (klienta)",
            description = "Zwraca pełne informacje o aktualnie uwierzytelnionym użytkowniku. Wymaga ważnego tokena JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Szczegóły użytkownika zostały zwrócone poprawnie"),
                    @ApiResponse(responseCode = "401", description = "Brak lub nieprawidłowy token JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Brak dostępu do zasobu", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping
    @ResponseStatus(OK)
    AuthDetailsDto me(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return authDetailsFacade.findOneById(identity.id());
    }

}
