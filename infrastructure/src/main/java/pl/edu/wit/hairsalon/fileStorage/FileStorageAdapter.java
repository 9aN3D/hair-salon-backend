package pl.edu.wit.hairsalon.fileStorage;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pl.edu.wit.hairsalon.uploadableFile.FileStoragePort;
import pl.edu.wit.hairsalon.uploadableFile.dto.FileTypeDto;

import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@ConfigurationProperties(prefix = "pl.edu.wit.file")
class FileStorageAdapter implements FileStoragePort {

    @NotNull
    private Map<FileTypeDto, FileStorageProperties> storage;

    public FileStorageAdapter() {
    }

    public FileStorageAdapter(Map<FileTypeDto, FileStorageProperties> storage) {
        this.storage = storage;
    }

    public String getUrl(FileTypeDto type) {
        return ofNullable(storage.get(type))
                .map(FileStorageProperties::getUrl)
                .orElse("");
    }

    public Map<FileTypeDto, FileStorageProperties> getStorage() {
        return storage;
    }

    public void setStorage(Map<FileTypeDto, FileStorageProperties> storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FileStorageAdapter that)) return false;
        return Objects.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storage);
    }

}
