package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.uploadableFile.dto.FileTypeDto;

public interface FileStoragePort {

    String getUrl(FileTypeDto type);

}
