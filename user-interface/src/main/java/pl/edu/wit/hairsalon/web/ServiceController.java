package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.web.adapter.ServiceResponseAdapter;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.ServiceResponse;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za zarządzanie usługami fryzjerskimi.
 * <p>
 * Pozwala tworzyć, edytować oraz pobierać informacje o usługach.
 * Endpointy z prefiksem {@code /admin} dostępne są wyłącznie dla użytkowników z odpowiednimi uprawnieniami
 * i wymagają uwierzytelnienia przy użyciu JWT (Bearer token, nagłówek {@code Authorization}).
 * </p>
 *
 * @see ServiceResponseAdapter
 */
@RestController
@RequestMapping(value = "/api/v1")
class ServiceController {

    private final ServiceResponseAdapter serviceResponseAdapter;

    /**
     * Tworzy nową instancję kontrolera usług.
     *
     * @param serviceResponseAdapter adapter odpowiedzialny za przetwarzanie logiki usług
     */
    ServiceController(ServiceResponseAdapter serviceResponseAdapter) {
        this.serviceResponseAdapter = serviceResponseAdapter;
    }

    /**
     * Tworzy nową usługę fryzjerską.
     *
     * @param command dane usługi do utworzenia
     */
    @Operation(
            summary = "Utwórz nową usługę",
            description = "Dodaje nową usługę fryzjerską.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usługa została utworzona"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(value = "/admin/services", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void create(@NotNull @RequestBody ServiceCreateCommand command) {
        serviceResponseAdapter.create(command);
    }

    /**
     * Aktualizuje istniejącą usługę fryzjerską.
     *
     * @param serviceId identyfikator usługi
     * @param command   dane do aktualizacji
     */
    @Operation(
            summary = "Zaktualizuj usługę",
            description = "Aktualizuje dane istniejącej usługi fryzjerskiej.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Zaktualizowano pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono usługi", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PutMapping(value = "/admin/services/{serviceId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable String serviceId, @NotNull @RequestBody ServiceUpdateCommand command) {
        serviceResponseAdapter.update(serviceId, command);
    }

    /**
     * Zwraca szczegóły jednej usługi fryzjerskiej.
     *
     * @param serviceId identyfikator usługi
     * @return szczegóły usługi
     */
    @Operation(
            summary = "Pobierz szczegóły usługi",
            description = "Zwraca szczegóły usługi o podanym identyfikatorze.",
            security = @SecurityRequirement(name = "hair-salon-API"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono szczegóły usługi"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono usługi", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/admin/services/{serviceId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    ServiceResponse findOne(@PathVariable String serviceId) {
        return serviceResponseAdapter.findOne(serviceId);
    }

    /**
     * Zwraca listę dostępnych usług z możliwością filtrowania i paginacji.
     *
     * @param findQuery zapytanie filtrowania
     * @param pageable  parametry paginacji (domyślnie sortowanie po nazwie, maksymalnie 100 wyników)
     * @return stronicowana lista usług
     */
    @Operation(
            summary = "Pobierz listę usług",
            description = "Zwraca listę wszystkich dostępnych usług z uwzględnieniem paginacji i filtrowania.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono listę usług")
            }
    )
    @GetMapping(value = "/services", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    PagedResponse<ServiceResponse> findAll(@NotNull ServiceFindQuery findQuery, @NotNull @ParameterObject @PageableDefault(size = 100, sort = "name") Pageable pageable) {
        return serviceResponseAdapter.findAll(findQuery, pageable);
    }

}
