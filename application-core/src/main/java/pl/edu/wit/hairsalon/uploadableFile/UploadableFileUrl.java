package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

class UploadableFileUrl implements SelfValidator<UploadableFileUrl> {

    private final UploadableFile uploadableFile;

    public UploadableFileUrl(UploadableFile uploadableFile) {
        this.uploadableFile = uploadableFile;
    }

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
                .id(uploadableFileDto.id())
                .name(uploadableFileDto.name())
                .type(uploadableFileDto.type())
                .contentType(uploadableFileDto.contentType())
                .length(uploadableFileDto.length())
                .uploadDate(uploadableFileDto.uploadDate())
                .content(uploadableFileDto.contentSupplier())
                .downloadUrl(prepareDownloadUrl(fileStoragePort, uploadableFileDto))
                .build();
    }

    private String prepareDownloadUrl(FileStoragePort fileStoragePort, UploadableFileDto dto) {
        return ofNullable(dto.type())
                .map(fileStoragePort::getUrl)
                .map(url -> format(url, dto.id()))
                .orElse("");
    }

}
