package pl.edu.wit.hairsalon.web.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.hairsalon.web.response.Problem;
import pl.edu.wit.hairsalon.token.exception.InvalidCredentialsException;

import javax.naming.AuthenticationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Globalny handler wyjątków bezpieczeństwa (uwierzytelnianie i autoryzacja).
 * <p>
 * Obsługuje przypadki nieprawidłowych danych logowania oraz błędów związanych z procesem uwierzytelniania,
 * zwracając odpowiednie odpowiedzi HTTP w formacie {@code application/problem+json}.
 * </p>
 *
 * <p>Posiada najwyższy priorytet wśród {@link RestControllerAdvice}, dzięki adnotacji {@link Order}(100),
 * co pozwala mu przechwytywać wyjątki bezpieczeństwa zanim trafią do bardziej ogólnych handlerów.</p>
 *
 * @see InvalidCredentialsException
 * @see AuthenticationException
 * @see Advice
 */
@Order(100)
@RestControllerAdvice
class SecurityAdvice extends Advice {

    /**
     * Obsługuje wyjątek {@link InvalidCredentialsException}, typowo rzucany przy błędnych danych logowania.
     * Zwraca status {@code 401 Unauthorized}.
     *
     * @param exception wyjątek błędnych poświadczeń
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> invalidCredentialsException(InvalidCredentialsException exception, NativeWebRequest request) {
        return create(UNAUTHORIZED, exception, request);
    }

    /**
     * Obsługuje ogólne wyjątki uwierzytelniania {@link AuthenticationException}, np. brak tokenu,
     * jego nieważność lub inne błędy związane z tożsamością użytkownika.
     * Zwraca status {@code 403 Forbidden}.
     *
     * @param exception wyjątek uwierzytelniania
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> authenticationException(AuthenticationException exception, NativeWebRequest request) {
        return create(FORBIDDEN, exception, request);
    }

}
