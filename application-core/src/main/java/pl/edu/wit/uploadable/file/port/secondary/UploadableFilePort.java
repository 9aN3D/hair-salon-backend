package pl.edu.wit.uploadable.file.port.secondary;

import pl.edu.wit.uploadable.file.command.FileUploadCommand;
import pl.edu.wit.uploadable.file.dto.UploadableFileDto;

import java.util.Optional;

public interface UploadableFilePort {

    String store(FileUploadCommand command);

    Optional<UploadableFileDto> findOne(String fileId);

    void delete(String fileId);

}
