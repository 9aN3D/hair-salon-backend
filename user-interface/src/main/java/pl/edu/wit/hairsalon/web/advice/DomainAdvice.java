package pl.edu.wit.hairsalon.web.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.hairsalon.sharedkernel.exception.DomainException;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Order(150)
@RestControllerAdvice
class DomainAdvice extends Advice {

    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(DomainException exception, NativeWebRequest request) {
        return create(BAD_REQUEST, exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(NullPointerException exception, NativeWebRequest request) {
        return create(UNPROCESSABLE_ENTITY, exception, request);
    }

}
