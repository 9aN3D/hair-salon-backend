package pl.edu.wit.hairsalon.web.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(200)
@RestControllerAdvice
class GeneralAdvice extends Advice {

    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(Throwable exception, NativeWebRequest request) {
        return create(INTERNAL_SERVER_ERROR, exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException exception, NativeWebRequest request) {
        return create(FORBIDDEN, exception, request);
    }

}
