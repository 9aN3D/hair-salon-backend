package pl.edu.wit.domain.exception.member;

import pl.edu.wit.domain.exception.DomainException;

public class MemberAgreementsNotValidException extends DomainException {

    public MemberAgreementsNotValidException(String message) {
        super(message);
    }

}
