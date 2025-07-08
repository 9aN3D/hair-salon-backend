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

/**
 * Globalny handler wyjątków ogólnych i bezpieczeństwa.
 * <p>
 * Obsługuje wszystkie nieprzechwycone wyjątki oraz przypadki braku dostępu,
 * przekształcając je w odpowiedzi HTTP w formacie {@code application/problem+json}.
 * </p>
 *
 * <p>Działa jako ostatnia linia obrony w hierarchii {@link RestControllerAdvice},
 * posiadając niższy priorytet niż np. {@link DomainAdvice} dzięki adnotacji {@link Order}.</p>
 *
 * @see Advice
 * @see org.springframework.security.access.AccessDeniedException
 * @see Problem
 */
@Order(200)
@RestControllerAdvice
class GeneralAdvice extends Advice {

    /**
     * Obsługuje wszystkie nieprzechwycone wyjątki, zwracając status {@code 500 Internal Server Error}.
     *
     * @param exception dowolny nieoczekiwany wyjątek
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(Throwable exception, NativeWebRequest request) {
        return create(INTERNAL_SERVER_ERROR, exception, request);
    }

    /**
     * Obsługuje wyjątki związane z brakiem dostępu do zasobów, zwracając status {@code 403 Forbidden}.
     *
     * @param exception wyjątek braku uprawnień
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException exception, NativeWebRequest request) {
        return create(FORBIDDEN, exception, request);
    }

}
