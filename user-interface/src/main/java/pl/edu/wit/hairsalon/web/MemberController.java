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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.MemberResponseAdapter;
import pl.edu.wit.hairsalon.web.response.MemberResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiadający za operacje na klientach (members).
 * <p>
 * Endpointy z prefiksem {@code /admin} dostępne są wyłącznie dla użytkowników z odpowiednimi uprawnieniami
 * i wymagają uwierzytelnienia przy użyciu JWT (Bearer token, nagłówek {@code Authorization}).
 * </p>
 *
 * @see MemberResponseAdapter
 */
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class MemberController {

    private final MemberResponseAdapter memberResponseAdapter;

    /**
     * Tworzy kontroler do operacji na danych klientów.
     *
     * @param memberResponseAdapter adapter do komunikacji z warstwą aplikacyjną
     */
    MemberController(MemberResponseAdapter memberResponseAdapter) {
        this.memberResponseAdapter = memberResponseAdapter;
    }

    /**
     * Zwraca dane zalogowanego klienta.
     *
     * @param identity uwierzytelniony kontekst użytkownika
     * @return szczegóły klienta
     */
    @Operation(
            summary = "Pobierz dane zalogowanego klienta",
            description = "Zwraca dane klienta na podstawie tożsamości pobranej z tokena JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dane klienta zostały zwrócone poprawnie"),
                    @ApiResponse(responseCode = "401", description = "Brak lub nieprawidłowy token JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/members")
    @ResponseStatus(OK)
    MemberResponse findOne(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return memberResponseAdapter.findOne(identity.id());
    }

    /**
     * Aktualizuje dane zalogowanego klienta.
     *
     * @param identity uwierzytelniony kontekst użytkownika
     * @param command dane do aktualizacji
     */
    @Operation(
            summary = "Zaktualizuj dane zalogowanego klienta",
            description = "Aktualizuje dane klienta (np. imię, telefon, email). Operacja wymaga autoryzacji JWT.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dane zaktualizowano pomyślnie"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane wejściowe", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Brak lub nieprawidłowy token JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(value = "/members", consumes = APPLICATION_JSON_VALUE)
    void update(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull @RequestBody MemberUpdateCommand command) {
        memberResponseAdapter.update(identity.id(), command);
    }
    
    /**
     * Zwraca listę klientów spełniających kryteria wyszukiwania (endpoint administracyjny).
     *
     * @param findQuery  kryteria filtrowania
     * @param pageable   paginacja
     * @return stronicowana lista klientów
     */
    @Operation(
            summary = "Lista klientów (admin)",
            description = "Zwraca paginowaną listę klientów. Endpoint wymaga autoryzacji jako administrator (JWT).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista klientów zwrócona poprawnie"),
                    @ApiResponse(responseCode = "401", description = "Brak lub nieprawidłowy token JWT", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Brak dostępu", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/admin/members")
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    PagedResponse<MemberResponse> findAll(@NotNull MemberFindQuery findQuery, @NotNull Pageable pageable) {
        return memberResponseAdapter.findAll(findQuery, pageable);
    }

}
