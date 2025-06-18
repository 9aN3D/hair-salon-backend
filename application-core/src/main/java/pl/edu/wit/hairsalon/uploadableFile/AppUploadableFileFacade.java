package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

import static java.util.Objects.requireNonNull;

class AppUploadableFileFacade implements UploadableFileFacade {

    private final UploadableFilePort uploadableFilePort;
    private final FileStoragePort fileStoragePort;
    private final UploadableFileStorer uploadableFileStorer;

    AppUploadableFileFacade(UploadableFilePort uploadableFilePort, FileStoragePort fileStoragePort, UploadableFileStorer uploadableFileStorer) {
        this.uploadableFilePort = uploadableFilePort;
        this.fileStoragePort = fileStoragePort;
        this.uploadableFileStorer = uploadableFileStorer;
    }

    @Override
    public String store(FileUploadCommand command) {
        requireNonNull(command, "Uploadable command must not be null");
        return uploadableFileStorer.store(command);
    }

    @Override
    public UploadableFileDto findOne(String fileId) {
        requireNonNull(fileId, "Uploadable file id must not be null");
        var uploadableFileUrl = new UploadableFileUrl(new UploadableFile(getOneByFileId(fileId)));
        return uploadableFileUrl.toDto(fileStoragePort);
    }

    @Override
    public void delete(String fileId) {
        requireNonNull(fileId, "Uploadable file id must not be null");
        var uploadableFileDto = getOneByFileId(fileId);
        uploadableFilePort.delete(uploadableFileDto.id());
    }

    private UploadableFileDto getOneByFileId(String fileId) {
        return uploadableFilePort.findOneOrThrow(fileId);
    }
}
