package pl.edu.wit.hairsalon.uploadablefile;

import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadablefile.dto.UploadableFileDto;

public interface UploadableFileFacade {

    String store(FileUploadCommand command);

    UploadableFileDto findOne(String fileId);

    void delete(String fileId);

}
