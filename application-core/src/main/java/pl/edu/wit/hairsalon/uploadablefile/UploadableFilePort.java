package pl.edu.wit.hairsalon.uploadablefile;

import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadablefile.dto.UploadableFileDto;

import java.util.Optional;

public interface UploadableFilePort {

    String store(FileUploadCommand command);

    Optional<UploadableFileDto> findOne(String fileId);

    UploadableFileDto findOneOrThrow(String fileId);

    void delete(String fileId);

}
