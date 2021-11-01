package pl.edu.wit.hairsalon.member.exception;

import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
