package pl.edu.wit.hairsalon.web;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.web.adapter.UploadableFileResponseAdapter;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping(value = "/api/v1/files")
class UploadableFileController {

    private final UploadableFileResponseAdapter fileFacade;

    UploadableFileController(UploadableFileResponseAdapter fileFacade) {
        this.fileFacade = fileFacade;
    }

    @GetMapping(value = "/{fileId}/image", produces = {IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE})
    @ResponseStatus(OK)
    ResponseEntity<InputStreamResource> getOneImage(@PathVariable String fileId) {
        return fileFacade.getOneImage(fileId);
    }

}
