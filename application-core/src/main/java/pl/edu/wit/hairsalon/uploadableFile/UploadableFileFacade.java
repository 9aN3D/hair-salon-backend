package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

public interface UploadableFileFacade {

    String store(FileUploadCommand command);

    UploadableFileDto findOne(String fileId);

    void delete(String fileId);

}
