package pl.edu.wit.api.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.api.response.Problem;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public abstract class Advice {

    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request) {
        return create(status, exception, request, null);
    }
    
    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request, HttpHeaders headers) {
        return create(toProblem(status, exception, request), headers);
    }

    protected final ResponseEntity<Problem> create(Problem problem, HttpHeaders headers) {
        return ResponseEntity.status(getOrDefault(problem))
                .headers(headers)
                .body(problem);
    }

    private HttpStatus getOrDefault(Problem problem) {
        return ofNullable(problem.getStatus()).orElse(INTERNAL_SERVER_ERROR);
    }
    
    private Problem toProblem(HttpStatus status, Throwable exception, NativeWebRequest request) {
        return Problem.builder()
                .title(status.getReasonPhrase())
                .status(status)
                .detail(exception.getMessage())
                .build();
    }
    
}
