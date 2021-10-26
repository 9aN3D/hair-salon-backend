package pl.edu.wit.web.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.web.response.Problem;
import pl.edu.wit.common.exception.DomainException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(150)
@RestControllerAdvice
public class DomainAdvice extends Advice {

    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(DomainException exception, NativeWebRequest request) {
        return create(BAD_REQUEST, exception, request);
    }

}
