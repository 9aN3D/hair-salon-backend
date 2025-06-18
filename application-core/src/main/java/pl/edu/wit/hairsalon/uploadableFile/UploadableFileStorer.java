package pl.edu.wit.hairsalon.uploadableFile;

import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadableFile.exception.UploadableFileStoreException;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

class UploadableFileStorer {

    private final UploadableFilePort uploadableFilePort;

    UploadableFileStorer(UploadableFilePort uploadableFilePort) {
        this.uploadableFilePort = uploadableFilePort;
    }

    public String store(FileUploadCommand command) {
        Objects.requireNonNull(command, "File upload command must not be null");
        verifySize(command);
        return tryStore(command);
    }

    private void verifySize(FileUploadCommand command) {
        ofNullable(command.size())
                .filter(this::hasPositiveValue)
                .orElseThrow(() -> new ValidationException("File upload command length must not be null or negative"));
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

}
