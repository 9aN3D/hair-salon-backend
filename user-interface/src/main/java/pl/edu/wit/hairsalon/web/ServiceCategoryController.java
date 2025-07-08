package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ServiceCategoryResponseAdapter;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.ServiceCategoryResponse;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST do zarządzania kategoriami usług fryzjerskich.
 * <p>
 * Umożliwia tworzenie, aktualizowanie i pobieranie kategorii usług.
 * </p>
 *
 * @see ServiceCategoryResponseAdapter
 */
@RestController
@RequestMapping(value = "/api/v1")
class ServiceCategoryController {

    private final ServiceCategoryResponseAdapter serviceCategoryResponseAdapter;

    /**
     * Tworzy nową instancję kontrolera kategorii usług.
     *
     * @param serviceCategoryResponseAdapter adapter odpowiedzialny za operacje na kategoriach usług
     */
    ServiceCategoryController(ServiceCategoryResponseAdapter serviceCategoryResponseAdapter) {
        this.serviceCategoryResponseAdapter = serviceCategoryResponseAdapter;
    }

    /**
     * Tworzy nową kategorię usług fryzjerskich.
     *
     * @param command dane nowej kategorii
     */
    @Operation(
            summary = "Utwórz nową kategorię usług",
            description = "Tworzy nową kategorię usług fryzjerskich.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Kategoria została utworzona"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(value = "/admin/services/categories", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void create(@NotNull @RequestBody ServiceCategoryCreateCommand command) {
        serviceCategoryResponseAdapter.create(command);
    }

    /**
     * Aktualizuje istniejącą kategorię usług.
     *
     * @param serviceCategoryId identyfikator kategorii
     * @param command           dane do aktualizacji
     */
    @Operation(
            summary = "Zaktualizuj kategorię usług",
            description = "Aktualizuje istniejącą kategorię usług fryzjerskich.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Zaktualizowano pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono kategorii", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PutMapping(value = "/admin/services/categories/{serviceCategoryId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @SecurityRequirement(name = "hair-salon-API")
    void update(@PathVariable String serviceCategoryId, @NotNull @RequestBody ServiceCategoryUpdateCommand command) {
        serviceCategoryResponseAdapter.update(serviceCategoryId, command);
    }

    /**
     * Zwraca wszystkie dostępne kategorie usług z uwzględnieniem paginacji i filtrowania.
     *
     * @param findQuery zapytanie filtrowania
     * @param pageable  parametry paginacji
     * @return stronicowana lista kategorii usług
     */
    @Operation(
            summary = "Pobierz kategorie usług",
            description = "Zwraca listę dostępnych kategorii usług fryzjerskich.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono listę kategorii")
            }
    )
    @GetMapping(value = "/services/categories", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<ServiceCategoryResponse> findAll(@NotNull ServiceCategoryFindQuery findQuery, @NotNull Pageable pageable) {
        return serviceCategoryResponseAdapter.findAll(findQuery, pageable);
    }

}
