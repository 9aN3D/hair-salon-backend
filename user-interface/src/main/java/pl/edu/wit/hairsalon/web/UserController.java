package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;
import pl.edu.wit.hairsalon.web.adapter.UserResponseAdapter;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.UserResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za zarządzanie użytkownikami systemu.
 * Endpointy dostępne tylko dla administratorów.
 */
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class UserController {

    private final UserResponseAdapter userResponseAdapter;

    /**
     * Tworzy kontroler użytkowników (role: ADMIN).
     *
     * @param userResponseAdapter adapter odpowiedzialny za logikę odczytu danych użytkownika
     */
    UserController(UserResponseAdapter userResponseAdapter) {
        this.userResponseAdapter = userResponseAdapter;
    }

    /**
     * Zwraca dane pojedynczego użytkownika na podstawie identyfikatora.
     *
     * @param authDetailsId identyfikator użytkownika
     * @return dane użytkownika
     */
    @Operation(
            summary = "Pobierz użytkownika",
            description = "Zwraca dane użytkownika na podstawie jego identyfikatora.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dane użytkownika zostały zwrócone poprawnie"),
                    @ApiResponse(responseCode = "403", description = "Brak dostępu", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono użytkownika", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/admin/users/{authDetailsId}")
    @ResponseStatus(OK)
    UserResponse findOne(@PathVariable String authDetailsId) {
        return userResponseAdapter.findOne(authDetailsId);
    }

    /**
     * Zwraca stronicowaną listę użytkowników zgodnie z podanym zapytaniem filtrującym.
     *
     * @param findQuery kryteria wyszukiwania użytkowników
     * @param pageable  parametry stronicowania
     * @return lista użytkowników w formie stronicowanej
     */
    @Operation(
            summary = "Pobierz listę użytkowników",
            description = "Zwraca stronicowaną listę użytkowników według przekazanych kryteriów wyszukiwania.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono listę użytkowników"),
                    @ApiResponse(responseCode = "403", description = "Brak dostępu", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/admin/users")
    @ResponseStatus(OK)
    PagedResponse<UserResponse> findAll(@NotNull UserFindQuery findQuery, @NotNull Pageable pageable) {
        return userResponseAdapter.findAll(findQuery, pageable);
    }

}
