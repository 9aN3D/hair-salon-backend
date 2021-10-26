package pl.edu.wit.uploadable.file.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.common.domain.NotBlankString;
import pl.edu.wit.uploadable.file.dto.UploadableFileDto;
import pl.edu.wit.common.exception.ValidationException;

import java.io.InputStream;
import java.time.LocalDateTime;

import static java.util.Optional.ofNullable;

@Builder
@ToString(exclude = "content")
@EqualsAndHashCode(of = "id")
public class UploadableFile {

    private final String id;
    private final String name;
    private final FileType type;
    private final String contentType;
    private final Long length;
    private final LocalDateTime uploadDate;
    private final InputStream content;

    public UploadableFile(String id,
                          String name,
                          FileType type,
                          String contentType,
                          Long length,
                          LocalDateTime uploadDate,
                          InputStream content) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
        this.type = type;
        this.contentType = contentType;
        this.length = ofNullable(length)
                .filter(this::hasPositiveLength)
                .orElseThrow(() -> new ValidationException("Uploadable file length cannot be null or negative"));
        this.uploadDate = ofNullable(uploadDate)
                .orElseThrow(() -> new ValidationException("Uploadable file upload date cannot be null"));
        this.content = ofNullable(content)
                .orElseThrow(() -> new ValidationException("Uploadable file content cannot be null"));
    }

    public UploadableFile(UploadableFileDto dto) {
        this(
                dto.getId(),
                dto.getName(),
                FileType.getFileType(dto.getContentType()),
                dto.getContentType(),
                dto.getLength(),
                dto.getUploadDate(),
                dto.getContent()
        );
    }

    public UploadableFileDto toDto() {
        return UploadableFileDto.builder()
                .id(id)
                .name(name)
                .type(type)
                .contentType(contentType)
                .length(length)
                .uploadDate(uploadDate)
                .content(content)
                .build();
    }

    private boolean hasPositiveLength(Long value) {
        return value.compareTo(0L) > 0;
    }

}
