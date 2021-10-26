package pl.edu.wit.uploadable.file.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import pl.edu.wit.uploadable.file.domain.FileType;

import java.io.InputStream;
import java.time.LocalDateTime;

@Value
@Builder
@ToString(exclude = "content")
@EqualsAndHashCode(of = "id")
public class UploadableFileDto {

    String id;

    String name;

    FileType type;

    String contentType;

    Long length;

    LocalDateTime uploadDate;

    InputStream content;

    String downloadUrl;

}
