package pl.edu.wit.hairsalon.uploadablefile;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;
import pl.edu.wit.hairsalon.uploadablefile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.uploadablefile.exception.UploadableFileStoreException;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
class UploadableFileStorer {

    private final UploadableFilePort uploadableFilePort;

    public String store(FileUploadCommand command) {
        Objects.requireNonNull(command, "File upload command must not be null");
        verifySize(command);
        return tryStore(command);
    }

    private void verifySize(FileUploadCommand command) {
        ofNullable(command.getSize())
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
