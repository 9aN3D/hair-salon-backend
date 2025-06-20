package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

record UploadableFile(
        String id,
        String name,
        FileType type,
        String contentType,
        Long length,
        LocalDateTime uploadDate,
        Supplier<InputStream> contentSupplier
) implements SelfValidator<UploadableFile> {

    public UploadableFile(UploadableFileDto dto) {
        this(
                dto.id(),
                dto.name(),
                FileType.getFileType(dto.contentType()),
                dto.contentType(),
                dto.length(),
                dto.uploadDate(),
                dto.contentSupplier()
        );
    }

    @Override
    public UploadableFile validate() {
        requireNonNull(length, "Uploadable file length must not be null");
        requireNonNull(uploadDate, "Uploadable file upload date must not be null");
        requireNonNull(contentSupplier, "Uploadable file content must not be null");
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
                .content(contentSupplier)
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    private boolean hasPositiveLength(Long value) {
        return value.compareTo(0L) > 0;
    }

    static class Builder {

        private String id;
        private String name;
        private FileType type;
        private String contentType;
        private Long length;
        private LocalDateTime uploadDate;
        private Supplier<InputStream> contentSupplier;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder type(FileType type) {
            this.type = type;
            return this;
        }

        Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        Builder length(Long length) {
            this.length = length;
            return this;
        }

        Builder uploadDate(LocalDateTime uploadDate) {
            this.uploadDate = uploadDate;
            return this;
        }

        Builder contentSupplier(Supplier<InputStream> contentSupplier) {
            this.contentSupplier = contentSupplier;
            return this;
        }

        UploadableFile build() {
            return new UploadableFile(id, name, type, contentType, length, uploadDate, contentSupplier);
        }

    }

}
