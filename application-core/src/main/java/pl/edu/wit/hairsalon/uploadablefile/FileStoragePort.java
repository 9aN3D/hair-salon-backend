package pl.edu.wit.hairsalon.uploadablefile;

import pl.edu.wit.hairsalon.uploadablefile.dto.FileTypeDto;

public interface FileStoragePort {

    String getUrl(FileTypeDto type);

}
