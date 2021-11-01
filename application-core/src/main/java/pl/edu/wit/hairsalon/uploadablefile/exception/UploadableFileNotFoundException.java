package pl.edu.wit.hairsalon.uploadablefile.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class UploadableFileNotFoundException extends DomainException {

    public UploadableFileNotFoundException(String message) {
        super(message);
    }

}
