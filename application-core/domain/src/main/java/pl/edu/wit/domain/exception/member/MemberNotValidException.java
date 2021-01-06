package pl.edu.wit.domain.exception.member;

public class MemberNotValidException extends RuntimeException {

    public MemberNotValidException(String message) {
        super(message);
    }

}
