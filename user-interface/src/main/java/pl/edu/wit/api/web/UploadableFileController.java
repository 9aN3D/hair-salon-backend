package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.facade.UploadableFileFacade;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/files")
public class UploadableFileController {

    private final UploadableFileFacade fileFacade;

    @GetMapping(value = "/{fileId}/image", produces = {IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE})
    @ResponseStatus(OK)
    @ResponseBody
    public byte[] getOneImage(@PathVariable String fileId) {
        return fileFacade.getOneImage(fileId);
    }

}
