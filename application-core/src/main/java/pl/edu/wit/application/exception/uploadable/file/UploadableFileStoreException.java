package pl.edu.wit.application.exception.uploadable.file;

import pl.edu.wit.application.exception.DomainException;

public class UploadableFileStoreException extends DomainException {

    public UploadableFileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
