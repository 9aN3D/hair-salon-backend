package pl.edu.wit.hairsalon.uploadableFile.exception;

import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;

public class UploadableFileNotFoundException extends DomainException {

    public UploadableFileNotFoundException(String message) {
        super(message);
    }

}
