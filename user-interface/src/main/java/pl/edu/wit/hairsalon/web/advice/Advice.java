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

/**
 * Abstrakcyjna klasa pomocnicza służąca do budowania odpowiedzi HTTP w formacie {@code application/problem+json}
 * zgodnie ze specyfikacją RFC 7807.
 * <p>
 * Zapewnia spójny sposób tworzenia instancji {@link Problem} na podstawie wyjątków,
 * statusów HTTP oraz opcjonalnych danych o naruszeniach walidacyjnych.
 * </p>
 *
 * <p>Klasy dziedziczące mogą używać metod `create(...)` do budowania odpowiedzi w kontrolerach typu {@code @ControllerAdvice}.</p>
 *
 * @see Problem
 * @see org.springframework.web.context.request.NativeWebRequest
 * @see org.springframework.http.ResponseEntity
 */
abstract class Advice {

    /**
     * Tworzy odpowiedź na podstawie podanego wyjątku, statusu oraz pojedynczego naruszenia.
     *
     * @param status    kod HTTP
     * @param exception wyjątek źródłowy
     * @param request   kontekst żądania
     * @param violation opcjonalne naruszenie walidacyjne
     * @return odpowiedź HTTP z ciałem typu {@link Problem}
     */
    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request, Violation violation) {
        return create(toProblem(status, exception, request, violation), null);
    }

    /**
     * Tworzy odpowiedź na podstawie podanego wyjątku i statusu.
     *
     * @param status    kod HTTP
     * @param exception wyjątek źródłowy
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z ciałem typu {@link Problem}
     */
    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request) {
        return create(toProblem(status, exception, request, null), null);
    }

    /**
     * Tworzy odpowiedź na podstawie podanego wyjątku, statusu oraz nagłówków HTTP.
     *
     * @param status    kod HTTP
     * @param exception wyjątek źródłowy
     * @param request   kontekst żądania
     * @param headers   dodatkowe nagłówki HTTP
     * @return odpowiedź HTTP z ciałem typu {@link Problem}
     */
    protected final ResponseEntity<Problem> create(HttpStatus status, Throwable exception, NativeWebRequest request, HttpHeaders headers) {
        return create(toProblem(status, exception, request, null), headers);
    }


    /**
     * Tworzy odpowiedź HTTP na podstawie gotowego obiektu {@link Problem} i nagłówków.
     *
     * @param problem gotowy obiekt opisujący problem
     * @param headers nagłówki HTTP (opcjonalne)
     * @return odpowiedź HTTP z problemem jako ciało
     */
    protected final ResponseEntity<Problem> create(Problem problem, HttpHeaders headers) {
        return ResponseEntity.status(getOrDefault(problem))
                .headers(headers)
                .body(problem);
    }

    /**
     * Zwraca status HTTP z obiektu {@link Problem}, lub {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * jeśli nie został określony.
     *
     * @param problem obiekt problemu
     * @return kod statusu HTTP
     */
    private HttpStatus getOrDefault(Problem problem) {
        return ofNullable(problem.status())
                .orElse(INTERNAL_SERVER_ERROR);
    }

    /**
     * Buduje instancję {@link Problem} na podstawie wyjątku, statusu i opcjonalnego naruszenia walidacyjnego.
     *
     * @param status    kod HTTP
     * @param exception wyjątek źródłowy
     * @param request   kontekst żądania
     * @param violation naruszenie walidacji (opcjonalnie)
     * @return zbudowany obiekt problemu
     */
    private Problem toProblem(HttpStatus status, Throwable exception, NativeWebRequest request, Violation violation) {
        return Problem.builder()
                .title(status.getReasonPhrase())
                .status(status)
                .detail(exception.getMessage())
                .violations(nonNull(violation) ? List.of(violation) : null)
                .build();
    }

}
