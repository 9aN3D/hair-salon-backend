package pl.edu.wit.hairsalon.uploadablefile;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;
import pl.edu.wit.hairsalon.uploadablefile.dto.UploadableFileDto;

import java.io.InputStream;
import java.time.LocalDateTime;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Builder
@RequiredArgsConstructor
@ToString(exclude = "content")
@EqualsAndHashCode(of = "id")
class UploadableFile implements SelfValidator<UploadableFile> {

    private final String id;
    private final String name;
    private final FileType type;
    private final String contentType;
    private final Long length;
    private final LocalDateTime uploadDate;
    private final InputStream content;

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

    @Override
    public UploadableFile validate() {
        requireNonNull(length, "Uploadable file length must not be null");
        requireNonNull(uploadDate, "Uploadable file upload date must not be null");
        requireNonNull(content, "Uploadable file content must not be null");
        validate(new NotBlankString(id), new NotBlankString(name));
        if (!hasPositiveLength(length)) {
            throw new ValidationException(format("Uploadable file length negative %d", length));
        }
        return this;
    }

    public UploadableFileDto toDto() {
        return UploadableFileDto.builder()
                .id(id)
                .name(name)
                .type(type.toDto())
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
