package pl.edu.wit.hairsalon.web.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.web.response.Violation;

import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

abstract class Advice {

    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request, Violation violation) {
        return create(toProblem(status, exception, request, violation), null);
    }

    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request) {
        return create(toProblem(status, exception, request, null), null);
    }

    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request, HttpHeaders headers) {
        return create(toProblem(status, exception, request, null), headers);
    }

    protected final ResponseEntity<Problem> create(Problem problem, HttpHeaders headers) {
        return ResponseEntity.status(getOrDefault(problem))
                .headers(headers)
                .body(problem);
    }

    private HttpStatus getOrDefault(Problem problem) {
        return ofNullable(problem.status())
                .orElse(INTERNAL_SERVER_ERROR);
    }

    private Problem toProblem(HttpStatus status, Throwable exception, NativeWebRequest request, Violation violation) {
        return Problem.builder()
                .title(status.getReasonPhrase())
                .status(status)
                .detail(exception.getMessage())
                .violations(nonNull(violation) ? List.of(violation) : null)
                .build();
    }

}
