package pl.edu.wit.api.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.api.response.Problem;
import pl.edu.wit.domain.exception.token.InvalidCredentialsException;

import javax.naming.AuthenticationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Order(1)
@RestControllerAdvice
public class SecurityAdvice extends Advice {

    @ExceptionHandler
    public ResponseEntity<Problem> invalidCredentialsException(InvalidCredentialsException exception, NativeWebRequest request) {
        return create(UNAUTHORIZED, exception, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> authenticationException(AuthenticationException exception, NativeWebRequest request) {
        return create(FORBIDDEN, exception, request);
    }

}
