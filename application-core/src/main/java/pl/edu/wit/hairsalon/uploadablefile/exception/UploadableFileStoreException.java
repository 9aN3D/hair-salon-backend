package pl.edu.wit.hairsalon.uploadablefile.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class UploadableFileStoreException extends DomainException {

    public UploadableFileStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
