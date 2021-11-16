package pl.edu.wit.hairsalon.uploadableFile.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.InputStream;
import java.time.LocalDateTime;

@Value
@Builder
@ToString(exclude = "content")
@EqualsAndHashCode(of = "id")
public class UploadableFileDto {

    String id;

    String name;

    FileTypeDto type;

    String contentType;

    Long length;

    LocalDateTime uploadDate;

    InputStream content;

    String downloadUrl;

}
