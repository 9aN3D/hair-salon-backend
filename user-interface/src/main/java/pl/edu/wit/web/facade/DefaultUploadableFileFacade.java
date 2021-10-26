package pl.edu.wit.web.facade;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pl.edu.wit.uploadable.file.port.primary.UploadableFileService;

@Service
@RequiredArgsConstructor
public class DefaultUploadableFileFacade implements UploadableFileFacade {

    private final UploadableFileService uploadableFileService;

    @Override
    @SneakyThrows
    public byte[] getOneImage(String fileId) {
        var uploadableFile = uploadableFileService.findOne(fileId);
        return uploadableFile.getContent().readAllBytes();
    }

}
