package pl.edu.wit.hairsalon.setting.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class SettingNotFoundException extends DomainException {

    public SettingNotFoundException(String message) {
        super(message);
    }

}
