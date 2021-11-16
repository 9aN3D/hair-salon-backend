package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import java.util.Optional;

public interface UploadableFilePort {

    String store(FileUploadCommand command);

    Optional<UploadableFileDto> findOne(String fileId);

    UploadableFileDto findOneOrThrow(String fileId);

    void delete(String fileId);

}
