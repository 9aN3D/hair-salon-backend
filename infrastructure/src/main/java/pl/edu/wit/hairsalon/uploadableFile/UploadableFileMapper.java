package pl.edu.wit.hairsalon.uploadableFile;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;

@Component
class UploadableFileMapper {

    public UploadableFileDto toDto(GridFSFile gridFSFile, GridFsResource resource) {
        return UploadableFileDto.builder()
                .id(gridFSFile.getObjectId().toString())
                .name(gridFSFile.getFilename())
                .length(Long.valueOf(gridFSFile.getMetadata().get("fileSize").toString()))
                .uploadDate(gridFSFile.getUploadDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .contentType(gridFSFile.getMetadata().get("_contentType").toString())
                .content(tryGetInputStream(resource))
                .build();
    }

    private InputStream tryGetInputStream(GridFsResource resource) {
        try (var result = resource.getInputStream()) {
            return result;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
