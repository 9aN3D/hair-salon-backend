package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;

@Service
@RequiredArgsConstructor
public class UploadableFileResponseAdapter {

    private final UploadableFileFacade uploadableFileFacade;

    @SneakyThrows
    public byte[] getOneImage(String fileId) {
        var uploadableFile = uploadableFileFacade.findOne(fileId);
        return uploadableFile.getContent().readAllBytes();
    }

}
