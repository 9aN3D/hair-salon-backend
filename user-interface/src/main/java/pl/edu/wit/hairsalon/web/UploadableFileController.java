package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import pl.edu.wit.hairsalon.web.adapter.UploadableFileResponseAdapter;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * Kontroler REST odpowiedzialny za udostępnianie plików przesyłanych do systemu.
 * <p>
 * Obsługuje jedynie obrazy (PNG, JPEG, GIF).
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/files")
class UploadableFileController {

    private final UploadableFileResponseAdapter fileFacade;

    /**
     * Tworzy nową instancję kontrolera plików.
     *
     * @param fileFacade fasada odpowiedzialna za dostęp do plików
     */
    UploadableFileController(UploadableFileResponseAdapter fileFacade) {
        this.fileFacade = fileFacade;
    }

    /**
     * Zwraca obraz na podstawie identyfikatora pliku.
     *
     * @param fileId   identyfikator pliku
     * @param response obiekt odpowiedzi HTTP do ustawienia nagłówków
     * @return strumień zawartości obrazu
     */
    @Operation(
            summary = "Pobierz obraz",
            description = "Zwraca obraz (PNG, JPEG lub GIF) na podstawie identyfikatora pliku.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Zwrócono obraz"),
                    @ApiResponse(responseCode = "404", description = "Nie znaleziono pliku o podanym identyfikatorze", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Wystąpił błąd podczas odczytu pliku", content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = Problem.class))
                    })
            }
    )
    @GetMapping(value = "/{fileId}/image", produces = {IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE})
    @ResponseStatus(OK)
    StreamingResponseBody getOneImage(@PathVariable String fileId, HttpServletResponse response) {
        return fileFacade.getOneImage(fileId, response);
    }

}
