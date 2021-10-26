package pl.edu.wit.file.storage.adapter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pl.edu.wit.uploadable.file.domain.FileType;
import pl.edu.wit.uploadable.file.port.secondary.FileStorageProvider;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@ConfigurationProperties(prefix = "pl.edu.wit.file")
public class FileStorageProperties implements FileStorageProvider {

    @NotNull
    private Map<FileType, FileStorageContent> storage;

    public String getUrl(FileType type) {
        return ofNullable(storage.get(type))
                .map(FileStorageContent::getUrl)
                .orElse(null);
    }

}
