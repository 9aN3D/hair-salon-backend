package pl.edu.wit.hairsalon.uploadablefile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadablefile.dto.UploadableFileDto;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
class AppUploadableFileFacade implements UploadableFileFacade {

    private final UploadableFilePort uploadableFilePort;
    private final FileStoragePort fileStoragePort;
    private final UploadableFileStorer uploadableFileStorer;

    @Override
    public String store(FileUploadCommand command) {
        log.trace("Storing file {command: {}}", command);
        requireNonNull(command, "Uploadable command must not be null");
        var fileId = uploadableFileStorer.store(command);
        log.info("Stored file {fileId: {}}", fileId);
        return fileId;
    }

    @Override
    public UploadableFileDto findOne(String fileId) {
        log.trace("Getting file {fileId: {}}", fileId);
        requireNonNull(fileId, "Uploadable file id must not be null");
        var uploadableFileUrl = new UploadableFileUrl(new UploadableFile(getOneByFileId(fileId)));
        var uploadableFileDto = uploadableFileUrl.toDto(fileStoragePort);
        log.info("Getting file {dto: {}}", uploadableFileUrl);
        return uploadableFileDto;
    }

    @Override
    public void delete(String fileId) {
        log.trace("Deleting file {fileId: {}}", fileId);
        requireNonNull(fileId, "Uploadable file id must not be null");
        var uploadableFileDto = getOneByFileId(fileId);
        uploadableFilePort.delete(uploadableFileDto.getId());
        log.trace("Deleted file {fileId: {}}", fileId);
    }

    private UploadableFileDto getOneByFileId(String fileId) {
        return uploadableFilePort.findOneOrThrow(fileId);
    }

}
