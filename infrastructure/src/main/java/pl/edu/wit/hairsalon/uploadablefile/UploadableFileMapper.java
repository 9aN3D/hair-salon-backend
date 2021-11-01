package pl.edu.wit.hairsalon.uploadablefile;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.uploadablefile.dto.UploadableFileDto;

import java.time.ZoneId;

@Component
class UploadableFileMapper {

    @SneakyThrows
    public UploadableFileDto toDto(GridFSFile gridFSFile, GridFsResource resource) {
        return UploadableFileDto.builder()
                .id(gridFSFile.getObjectId().toString())
                .name(gridFSFile.getFilename())
                .length(Long.valueOf(gridFSFile.getMetadata().get("fileSize").toString()))
                .uploadDate(gridFSFile.getUploadDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .contentType(gridFSFile.getMetadata().get("_contentType").toString())
                .content(resource.getInputStream())
                .build();
    }

}
