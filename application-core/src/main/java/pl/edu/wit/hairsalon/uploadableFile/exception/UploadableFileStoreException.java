package pl.edu.wit.hairsalon.uploadableFile.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class UploadableFileStoreException extends DomainException {

    public UploadableFileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
