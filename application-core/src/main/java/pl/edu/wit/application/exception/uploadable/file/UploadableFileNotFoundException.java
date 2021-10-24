package pl.edu.wit.application.exception.uploadable.file;

import pl.edu.wit.application.exception.DomainException;

public class UploadableFileNotFoundException extends DomainException {

    public UploadableFileNotFoundException(String message) {
        super(message);
    }

}
