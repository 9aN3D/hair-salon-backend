package pl.edu.wit.hairsalon.web.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import pl.edu.wit.hairsalon.reservation.exception.ReservationCalculationException;
import pl.edu.wit.hairsalon.sharedKernel.exception.DomainException;
import pl.edu.wit.hairsalon.web.response.Problem;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Globalny handler wyjątków domenowych w aplikacji.
 * <p>
 * Obsługuje wyjątki związane z logiką domenową i przekształca je w odpowiedzi HTTP
 * w formacie {@code application/problem+json}, zgodnie ze specyfikacją RFC 7807.
 * </p>
 *
 * <p>Rozszerza {@link Advice}, korzystając z jego metod pomocniczych do budowania odpowiedzi.</p>
 *
 * <p>Adnotacja {@link Order} pozwala ustawić kolejność wykonywania w przypadku wielu handlerów.</p>
 *
 * @see Advice
 * @see Problem
 * @see DomainException
 * @see ReservationCalculationException
 */
@Order(150)
@RestControllerAdvice
class DomainAdvice extends Advice {

    /**
     * Obsługuje wyjątki biznesowe klasy {@link DomainException}, zwracając status {@code 400 Bad Request}.
     *
     * @param exception wyjątek domenowy
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(DomainException exception, NativeWebRequest request) {
        return create(BAD_REQUEST, exception, request);
    }

    /**
     * Obsługuje wyjątki kalkulacji rezerwacji {@link ReservationCalculationException}, zwracając status {@code 409 Conflict}.
     *
     * @param exception wyjątek kalkulacji
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(ReservationCalculationException exception, NativeWebRequest request) {
        return create(CONFLICT, exception, request);
    }

    /**
     * Fallback dla nieoczekiwanych błędów typu {@link NullPointerException}.
     * <p>
     * Może pomóc w szybkim identyfikowaniu braków w kodzie produkcyjnym.
     * Zwraca status {@code 422 Unprocessable Entity}.
     * </p>
     *
     * @param exception wyjątek null-pointer
     * @param request   kontekst żądania
     * @return odpowiedź HTTP z opisem problemu
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleThrowable(NullPointerException exception, NativeWebRequest request) {
        return create(UNPROCESSABLE_ENTITY, exception, request);
    }

}
