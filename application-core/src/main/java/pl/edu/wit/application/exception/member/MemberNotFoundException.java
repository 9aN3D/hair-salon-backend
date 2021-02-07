package pl.edu.wit.application.exception.member;

import pl.edu.wit.application.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
