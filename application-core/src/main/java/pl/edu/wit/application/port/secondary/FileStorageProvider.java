package pl.edu.wit.application.port.secondary;

import pl.edu.wit.application.domain.model.uploadable.file.FileType;

public interface FileStorageProvider {

    String getUrl(FileType type);

}
