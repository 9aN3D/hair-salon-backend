package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.token.AccessTokenFacade;
import pl.edu.wit.hairsalon.token.command.AccessTokenGenerateCommand;
import pl.edu.wit.hairsalon.token.command.AccessTokenRefreshCommand;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za generowanie i odświeżanie tokenów JWT.
 * <p>
 * Umożliwia logowanie użytkownika oraz odnowienie tokenu dostępowego.
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/tokens", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
class TokenController {

    private final AccessTokenFacade accessTokenFacade;

    /**
     * Tworzy nową instancję kontrolera tokenów.
     *
     * @param accessTokenFacade fasada odpowiedzialna za logikę tokenów JWT
     */
    TokenController(AccessTokenFacade accessTokenFacade) {
        this.accessTokenFacade = accessTokenFacade;
    }

    /**
     * Generuje nowy token dostępowy (logowanie).
     *
     * @param command dane logowania
     * @return nowy token JWT wraz z tokenem odświeżającym
     */
    @Operation(
            summary = "Wygeneruj token JWT",
            description = "Zwraca token JWT na podstawie poprawnych danych uwierzytelniających.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono token JWT"),
                    @ApiResponse(responseCode = "401", description = "Nieprawidłowe dane logowania", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping
    @ResponseStatus(OK)
    AccessTokenDto generate(@RequestBody AccessTokenGenerateCommand command) {
        return accessTokenFacade.generate(command);
    }

    /**
     * Odświeża token dostępowy.
     *
     * @param command zawiera ważny token odświeżający
     * @return nowy token JWT
     */
    @Operation(
            summary = "Odśwież token JWT",
            description = "Zwraca nowy token JWT na podstawie ważnego tokena odświeżającego.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono nowy token"),
                    @ApiResponse(responseCode = "401", description = "Nieprawidłowy lub wygasły token odświeżający", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(value = "/refresh")
    @ResponseStatus(OK)
    AccessTokenDto refresh(@RequestBody AccessTokenRefreshCommand command) {
        return accessTokenFacade.refresh(command);
    }

}
