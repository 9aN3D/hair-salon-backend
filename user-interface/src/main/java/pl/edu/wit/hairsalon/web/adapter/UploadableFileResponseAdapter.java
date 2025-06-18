package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;

@Service
public class UploadableFileResponseAdapter {

    private final UploadableFileFacade uploadableFileFacade;

    public UploadableFileResponseAdapter(UploadableFileFacade uploadableFileFacade) {
        this.uploadableFileFacade = uploadableFileFacade;
    }

    public ResponseEntity<InputStreamResource> getOneImage(String fileId) {
        var uploadableFile = uploadableFileFacade.findOne(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadableFile.contentType()))
                .contentLength(uploadableFile.length())
                .cacheControl(CacheControl.noCache())
                .body(new InputStreamResource(uploadableFile.content()));
    }

}
