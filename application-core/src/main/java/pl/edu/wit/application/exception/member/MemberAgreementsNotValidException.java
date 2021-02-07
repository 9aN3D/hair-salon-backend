package pl.edu.wit.application.exception.member;

import pl.edu.wit.application.exception.DomainException;

public class MemberAgreementsNotValidException extends DomainException {

    public MemberAgreementsNotValidException(String message) {
        super(message);
    }

}
