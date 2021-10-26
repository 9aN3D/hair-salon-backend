package pl.edu.wit.uploadable.file.port.primary;

import pl.edu.wit.uploadable.file.command.FileUploadCommand;
import pl.edu.wit.uploadable.file.dto.UploadableFileDto;

public interface UploadableFileService {

    String store(FileUploadCommand command);

    UploadableFileDto findOne(String fileId);

    void delete(String fileId);

}
