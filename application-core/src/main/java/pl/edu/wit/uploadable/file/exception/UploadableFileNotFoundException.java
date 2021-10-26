package pl.edu.wit.uploadable.file.exception;

import pl.edu.wit.common.exception.DomainException;

public class UploadableFileNotFoundException extends DomainException {

    public UploadableFileNotFoundException(String message) {
        super(message);
    }

}
