package pl.edu.wit.uploadable.file.domain;

import lombok.ToString;
import pl.edu.wit.uploadable.file.dto.UploadableFileDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.uploadable.file.port.secondary.FileStorageProvider;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@ToString(exclude = "fileStorageProvider")
public class UploadableFileUrl {

    private final UploadableFile uploadableFile;
    private final FileStorageProvider fileStorageProvider;

    public UploadableFileUrl(UploadableFile uploadableFile, FileStorageProvider fileStorageProvider) {
        this.uploadableFile = ofNullable(uploadableFile)
                .orElseThrow(() -> new ValidationException("Uploadable file cannot be null"));
        this.fileStorageProvider = ofNullable(fileStorageProvider)
                .orElseThrow(() -> new ValidationException("File storage provider cannot be null"));
    }

    public UploadableFileDto toDto() {
        var uploadableFileDto = uploadableFile.toDto();
        return UploadableFileDto.builder()
                .id(uploadableFileDto.getId())
                .name(uploadableFileDto.getName())
                .type(uploadableFileDto.getType())
                .contentType(uploadableFileDto.getContentType())
                .length(uploadableFileDto.getLength())
                .uploadDate(uploadableFileDto.getUploadDate())
                .content(uploadableFileDto.getContent())
                .downloadUrl(ofNullable(uploadableFileDto.getType())
                        .map(fileStorageProvider::getUrl)
                        .map(url -> format(url, uploadableFileDto.getId()))
                        .orElse(null))
                .build();
    }

}
