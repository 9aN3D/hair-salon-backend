package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.command.FileUploadCommand;
import pl.edu.wit.application.dto.UploadableFileDto;

import java.util.Optional;

public interface UploadableFilePort {

    String store(FileUploadCommand command);

    Optional<UploadableFileDto> findOne(String fileId);

    void delete(String fileId);

}
