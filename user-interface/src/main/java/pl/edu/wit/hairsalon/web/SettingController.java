package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;
import pl.edu.wit.hairsalon.web.response.Problem;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za zarządzanie ustawieniami systemowymi salonu.
 * <p>
 * Udostępnia operacje tworzenia, edycji i odczytu konfiguracji aplikacji.
 * </p>
 * <p>
 * Endpointy z prefiksem {@code /admin} dostępne są wyłącznie dla użytkowników z odpowiednimi uprawnieniami
 * i wymagają uwierzytelnienia przy użyciu JWT (Bearer token, nagłówek {@code Authorization}).
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1")
class SettingController {

    private final SettingFacade settingFacade;

    /**
     * Tworzy nową instancję kontrolera ustawień.
     *
     * @param settingFacade fasada odpowiedzialna za logikę ustawień
     */
    SettingController(SettingFacade settingFacade) {
        this.settingFacade = settingFacade;
    }

    /**
     * Tworzy nowe ustawienie.
     *
     * @param command dane ustawienia do utworzenia
     */
    @Operation(
            summary = "Utwórz nowe ustawienie",
            description = "Tworzy nowe ustawienie systemowe.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ustawienie zostało utworzone"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @SecurityRequirement(name = "hair-salon-API")
    @PostMapping(value = "/admin/settings", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void create(@NotNull @RequestBody SettingCreateCommand command) {
        settingFacade.create(command);
    }

    /**
     * Aktualizuje wartość istniejącego ustawienia.
     *
     * @param settingId identyfikator ustawienia
     * @param value     nowa wartość ustawienia
     */
    @Operation(
            summary = "Zaktualizuj wartość ustawienia",
            description = "Aktualizuje istniejące ustawienie na podstawie identyfikatora.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Zaktualizowano pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowa wartość", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono ustawienia", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @SecurityRequirement(name = "hair-salon-API")
    @PutMapping(value = "/admin/settings/{settingId}")
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable SettingIdDto settingId, @NotNull @RequestBody String value) {
        settingFacade.update(settingId, value);
    }

    /**
     * Pobiera jedno ustawienie na podstawie jego identyfikatora.
     *
     * @param settingId identyfikator ustawienia
     * @return obiekt ustawienia
     */
    @Operation(
            summary = "Pobierz jedno ustawienie",
            description = "Zwraca ustawienie o podanym identyfikatorze.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono ustawienie"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono ustawienia", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/settings/{settingId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    SettingDto findOne(@PathVariable SettingIdDto settingId) {
        return settingFacade.findOne(settingId);
    }

    /**
     * Pobiera wszystkie ustawienia w ramach danej grupy.
     *
     * @param findQuery zapytanie z identyfikatorem grupy ustawień
     * @return mapa identyfikatorów i wartości ustawień
     */
    @Operation(
            summary = "Pobierz wszystkie ustawienia grupy",
            description = "Zwraca mapę identyfikatorów i wartości ustawień przypisanych do wskazanej grupy.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono listę ustawień")
            }
    )
    @GetMapping(value = "/settings", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Map<SettingIdDto, String> findAll(@NotNull SettingGroupFindQuery findQuery) {
        return settingFacade.findAll(findQuery).stream()
                .collect(toMap(SettingDto::id, SettingDto::value));
    }

}
