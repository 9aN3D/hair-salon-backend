package pl.edu.wit.hairsalon.uploadableFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.dto.UploadableFileDto;

class LoggableUploadableFileFacade implements UploadableFileFacade {

    private final Logger log;
    private final UploadableFileFacade delegate;

    LoggableUploadableFileFacade(UploadableFileFacade delegate) {
        this.log = LoggerFactory.getLogger(LoggableUploadableFileFacade.class);
        this.delegate = delegate;
    }

    @Override
    public String store(FileUploadCommand command) {
        log.trace("Storing file {command: {}}", command);
        var fileId = delegate.store(command);
        log.info("Stored file {fileId: {}}", fileId);
        return fileId;
    }

    @Override
    public UploadableFileDto findOne(String fileId) {
        log.trace("Getting file {fileId: {}}", fileId);
        var dto = delegate.findOne(fileId);
        log.info("Getting file {dto: {}}", dto);
        return dto;
    }

    @Override
    public void delete(String fileId) {
        log.trace("Deleting file {fileId: {}}", fileId);
        delegate.delete(fileId);
        log.trace("Deleted file {fileId: {}}", fileId);
    }

}
