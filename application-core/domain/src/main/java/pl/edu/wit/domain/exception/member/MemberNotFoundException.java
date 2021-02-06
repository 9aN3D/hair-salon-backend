package pl.edu.wit.domain.exception.member;

import pl.edu.wit.domain.exception.DomainException;

public class MemberNotFoundException extends DomainException {

    public MemberNotFoundException(String message) {
        super(message);
    }

}
