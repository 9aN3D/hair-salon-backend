package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.dto.UploadableFileDto;

public interface UploadableFileService {

    String store(FileUploadCommand command);

    UploadableFileDto findOne(String fileId);

    void delete(String fileId);

}
