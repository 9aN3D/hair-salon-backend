package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.web.adapter.HairdresserResponseAdapter;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Kontroler REST odpowiadający za zarządzanie fryzjerami oraz ich dostępnością.
 * <p>
 * Udostępnia zarówno publiczne endpointy do pobierania informacji o fryzjerach,
 * jak i zabezpieczone operacje administracyjne wymagające autoryzacji przez JWT (Bearer Token).
 * Token JWT musi być przekazany w nagłówku {@code Authorization} zgodnie ze schematem {@code hair-salon-API}.
 * </p>
 *
 * <p>Publiczne endpointy:</p>
 * <ul>
 *     <li>{@code GET /hairdressers} – lista fryzjerów</li>
 *     <li>{@code GET /hairdressers/{id}} – szczegóły fryzjera</li>
 *     <li>{@code GET /hairdressers/{id}/available-start-times/{date}} – dostępne godziny</li>
 * </ul>
 *
 * <p>Endpointy administracyjne (wymagają JWT):</p>
 * <ul>
 *     <li>{@code POST /admin/hairdressers} – utwórz nowego fryzjera</li>
 *     <li>{@code POST /admin/hairdressers/{id}/photos} – dodaj zdjęcie profilowe</li>
 *     <li>{@code PUT /admin/hairdressers/{id}} – aktualizuj dane fryzjera</li>
 *     <li>{@code POST /admin/hairdressers/day-overrides} – dodaj wyjątek w dostępności</li>
 *     <li>{@code PUT /admin/hairdressers/{id}/day-overrides/{date}} – aktualizuj wyjątek</li>
 * </ul>
 *
 * @see HairdresserResponseAdapter
 * @see io.swagger.v3.oas.annotations.security.SecurityScheme
 */
@RestController
@RequestMapping(value = "/api/v1")
class HairdresserController {

    private final HairdresserResponseAdapter hairdresserResponseAdapter;

    HairdresserController(HairdresserResponseAdapter hairdresserResponseAdapter) {
        this.hairdresserResponseAdapter = hairdresserResponseAdapter;
    }

    /**
     * Tworzy nowego fryzjera.
     *
     * @param command dane do utworzenia fryzjera
     * @return ID utworzonego fryzjera
     */
    @Operation(summary = "Utwórz nowego fryzjera", security = @SecurityRequirement(name = "hair-salon-API"))
    @PostMapping(value = "/admin/hairdressers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    String create(@NotNull @RequestBody HairdresserCreateCommand command) {
        return hairdresserResponseAdapter.create(command);
    }

    /**
     * Przesyła zdjęcie profilowe fryzjera.
     *
     * @param hairdresserId ID fryzjera
     * @param file plik obrazu (multipart)
     */
    @Operation(summary = "Dodaj zdjęcie profilowe fryzjera", security = @SecurityRequirement(name = "hair-salon-API"))
    @PostMapping(value = "/admin/hairdressers/{hairdresserId}/photos", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void uploadPhoto(@PathVariable String hairdresserId, @RequestPart MultipartFile file) {
        hairdresserResponseAdapter.uploadPhoto(hairdresserId, file);
    }

    /**
     * Aktualizuje dane fryzjera.
     *
     * @param hairdresserId ID fryzjera
     * @param command dane do aktualizacji
     */
    @Operation(summary = "Aktualizuj fryzjera", security = @SecurityRequirement(name = "hair-salon-API"))
    @PutMapping(value = "/admin/hairdressers/{hairdresserId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void update(@PathVariable String hairdresserId, @NotNull @RequestBody HairdresserUpdateCommand command) {
        hairdresserResponseAdapter.update(hairdresserId, command);
    }

    /**
     * Zwraca szczegóły fryzjera.
     *
     * @param hairdresserId ID fryzjera
     * @return dane fryzjera
     */
    @Operation(summary = "Pobierz szczegóły fryzjera")
    @GetMapping(value = "/hairdressers/{hairdresserId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    HairdresserResponse findOne(@PathVariable String hairdresserId) {
        return hairdresserResponseAdapter.findOne(hairdresserId);
    }

    /**
     * Zwraca listę fryzjerów z paginacją.
     *
     * @param findQuery kryteria wyszukiwania
     * @param pageable parametry paginacji
     * @return stronicowana lista fryzjerów
     */
    @Operation(summary = "Pobierz listę fryzjerów")
    @GetMapping(value = "/hairdressers", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<HairdresserResponse> findAll(@NotNull HairdresserFindQuery findQuery, @NotNull Pageable pageable) {
        return hairdresserResponseAdapter.findAll(findQuery, pageable);
    }

    /**
     * Zwraca listę dostępnych godzin startowych dla danego fryzjera w podanym dniu.
     *
     * @param hairdresserId ID fryzjera
     * @param date data (yyyy-MM-dd)
     * @return lista godzin startowych
     */
    @Operation(summary = "Dostępne godziny startowe fryzjera")
    @GetMapping(value = "/hairdressers/{hairdresserId}/available-start-times/{date}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    List<LocalTime> getAvailableStartTimes(@PathVariable String hairdresserId, @PathVariable LocalDate date) {
        return hairdresserResponseAdapter.getAvailableStartTimes(hairdresserId, date);
    }

    /**
     * Tworzy wyjątek w harmonogramie pracy fryzjera (dzień niestandardowy).
     *
     * @param command dane dnia niestandardowego
     * @return ID wyjątku
     */
    @Operation(summary = "Utwórz dzień niestandardowy fryzjera", security = @SecurityRequirement(name = "hair-salon-API"))
    @PostMapping(value = "/admin/hairdressers/day-overrides", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    HairdresserDayOverrideIdDto createDayOverride(@NotNull @RequestBody HairdresserDayOverrideCreateCommand command) {
        return hairdresserResponseAdapter.createDayOverride(command);
    }

    /**
     * Aktualizuje istniejący wyjątek z harmonogramu pracy fryzjera.
     *
     * @param hairdresserId ID fryzjera
     * @param date data wyjątku
     * @param command dane aktualizacyjne
     */
    @Operation(summary = "Aktualizuj dzień niestandardowy fryzjera", security = @SecurityRequirement(name = "hair-salon-API"))
    @PutMapping(value = "/admin/hairdressers/{hairdresserId}/day-overrides/{date}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void updateDayOverride(@PathVariable String hairdresserId,
                           @PathVariable LocalDate date,
                           @NotNull @RequestBody HairdresserDayOverrideUpdateCommand command) {
        hairdresserResponseAdapter.updateDayOverride(new HairdresserDayOverrideIdDto(hairdresserId, date), command);
    }

}
