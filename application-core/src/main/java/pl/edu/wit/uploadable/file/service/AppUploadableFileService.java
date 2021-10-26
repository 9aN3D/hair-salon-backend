package pl.edu.wit.uploadable.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.wit.uploadable.file.command.FileUploadCommand;
import pl.edu.wit.uploadable.file.domain.UploadableFile;
import pl.edu.wit.uploadable.file.domain.UploadableFileUrl;
import pl.edu.wit.uploadable.file.dto.UploadableFileDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.uploadable.file.exception.UploadableFileNotFoundException;
import pl.edu.wit.uploadable.file.exception.UploadableFileStoreException;
import pl.edu.wit.uploadable.file.port.primary.UploadableFileService;
import pl.edu.wit.uploadable.file.port.secondary.FileStorageProvider;
import pl.edu.wit.uploadable.file.port.secondary.UploadableFilePort;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class AppUploadableFileService implements UploadableFileService {

    private final UploadableFilePort uploadableFilePort;
    private final FileStorageProvider fileStorageProvider;

    @Override
    public String store(FileUploadCommand command) {
        log.trace("Storing file {command: {}}", command);
        verifySize(command);
        String fileId = tryStore(command);
        log.info("Stored file {fileId: {}}", fileId);
        return fileId;
    }

    @Override
    public UploadableFileDto findOne(String fileId) {
        log.trace("Getting file {fileId: {}}", fileId);
        var uploadableFileUrl = new UploadableFileUrl(
                new UploadableFile(getOneByFileId(fileId)),
                fileStorageProvider);
        var uploadableFileDto = uploadableFileUrl.toDto();
        log.info("Getting file {dto: {}}", uploadableFileUrl);
        return uploadableFileDto;
    }

    @Override
    public void delete(String fileId) {
        log.trace("Deleting file {fileId: {}}", fileId);
        var uploadableFileDto = getOneByFileId(fileId);
        uploadableFilePort.delete(uploadableFileDto.getId());
        log.trace("Deleted file {fileId: {}}", fileId);
    }

    private void verifySize(FileUploadCommand command) {
        ofNullable(command.getSize())
                .filter(this::hasPositiveValue)
                .orElseThrow(() -> new ValidationException("File upload command length cannot be null or negative"));
    }

    private boolean hasPositiveValue(Long value) {
        return value.compareTo(0L) > 0;
    }

    private String tryStore(FileUploadCommand command) {
        try {
            return uploadableFilePort.store(command);
        } catch (Exception exception) {
            throw new UploadableFileStoreException(
                    format("Uploadable file not stored by command %s", command),
                    exception
            );
        }
    }

    private UploadableFileDto getOneByFileId(String fileId) {
        return uploadableFilePort.findOne(fileId)
                .orElseThrow(() -> new UploadableFileNotFoundException(
                        format("Uploadable file not found by fileId %s", fileId)
                ));
    }

}
