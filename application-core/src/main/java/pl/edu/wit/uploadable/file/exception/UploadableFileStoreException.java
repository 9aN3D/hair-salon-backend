package pl.edu.wit.uploadable.file.exception;

import pl.edu.wit.common.exception.DomainException;

public class UploadableFileStoreException extends DomainException {

    public UploadableFileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
