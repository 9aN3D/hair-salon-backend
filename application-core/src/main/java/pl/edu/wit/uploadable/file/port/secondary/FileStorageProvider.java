package pl.edu.wit.uploadable.file.port.secondary;

import pl.edu.wit.uploadable.file.domain.FileType;

public interface FileStorageProvider {

    String getUrl(FileType type);

}
