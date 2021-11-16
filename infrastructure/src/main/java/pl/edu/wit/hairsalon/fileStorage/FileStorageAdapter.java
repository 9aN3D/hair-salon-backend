package pl.edu.wit.hairsalon.fileStorage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pl.edu.wit.hairsalon.uploadableFile.FileStoragePort;
import pl.edu.wit.hairsalon.uploadableFile.dto.FileTypeDto;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@ConfigurationProperties(prefix = "pl.edu.wit.file")
class FileStorageAdapter implements FileStoragePort {

    @NotNull
    private Map<FileTypeDto, FileStorageProperties> storage;

    public String getUrl(FileTypeDto type) {
        return ofNullable(storage.get(type))
                .map(FileStorageProperties::getUrl)
                .orElse("");
    }

}
