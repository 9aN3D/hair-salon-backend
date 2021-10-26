package pl.edu.wit.member.exception;

import pl.edu.wit.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
