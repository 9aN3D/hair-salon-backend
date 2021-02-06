package pl.edu.wit.domain.exception.member;

import pl.edu.wit.domain.exception.DomainException;

public class MemberNotValidException extends DomainException {

    public MemberNotValidException(String message) {
        super(message);
    }

}
