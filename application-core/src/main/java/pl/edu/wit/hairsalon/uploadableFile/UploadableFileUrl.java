package pl.edu.wit.hairsalon.uploadableFile;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@ToString
@RequiredArgsConstructor
class UploadableFileUrl implements SelfValidator<UploadableFileUrl> {

    private final UploadableFile uploadableFile;

    @Override
    public UploadableFileUrl validate() {
        requireNonNull(uploadableFile, "Uploadable file must not be null");
        uploadableFile.validate();
        return this;
    }

    public UploadableFileDto toDto(FileStoragePort fileStoragePort) {
        requireNonNull(fileStoragePort, "Uploadable file must not be null");
        var uploadableFileDto = uploadableFile.toDto();
        return UploadableFileDto.builder()
                .id(uploadableFileDto.getId())
                .name(uploadableFileDto.getName())
                .type(uploadableFileDto.getType())
                .contentType(uploadableFileDto.getContentType())
                .length(uploadableFileDto.getLength())
                .uploadDate(uploadableFileDto.getUploadDate())
                .content(uploadableFileDto.getContent())
                .downloadUrl(prepareDownloadUrl(fileStoragePort, uploadableFileDto))
                .build();
    }

    private String prepareDownloadUrl(FileStoragePort fileStoragePort, UploadableFileDto dto) {
        return ofNullable(dto.getType())
                .map(fileStoragePort::getUrl)
                .map(url -> format(url, dto.getId()))
                .orElse("");
    }

}
