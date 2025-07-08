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
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.command.MemberRegisterCommand;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Kontroler REST odpowiedzialny za rejestrację nowych klientów.
 *
 * <p>
 * Endpointy nie wymagają uwierzytelnienia.
 * </p>
 *
 * @see MemberFacade#register(MemberRegisterCommand)
 */
@RestController
@RequestMapping("/api/v1/registration")
class RegistrationController {

    private final MemberFacade memberFacade;

    /**
     * Tworzy kontroler rejestracji.
     *
     * @param memberFacade fasada do operacji na klientach
     */
    RegistrationController(MemberFacade memberFacade) {
        this.memberFacade = memberFacade;
    }

    /**
     * Rejestruje nowego klienta w systemie.
     *
     * @param command dane rejestracyjne klienta
     */
    @Operation(
            summary = "Rejestracja nowego klienta",
            description = "Umożliwia rejestrację nowego konta klienta na podstawie podanych danych.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Dane rejestracyjne klienta"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rejestracja zakończona sukcesem"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane rejestracyjne", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void register(@RequestBody MemberRegisterCommand command) {
        memberFacade.register(command);
    }

}
