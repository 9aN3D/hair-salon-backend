package pl.edu.wit.application.exception.member;

import pl.edu.wit.application.exception.DomainException;

public class MemberNotValidException extends DomainException {

    public MemberNotValidException(String message) {
        super(message);
    }

}
