package pl.edu.wit.hairsalon.eventBus;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alias dla {@link TransactionalEventListener} z domyślną konfiguracją typową dla zdarzeń domenowych.
 * <p>
 * Stosowany do oznaczania metod słuchających zdarzeń w kontekście transakcyjnym — domyślnie
 * po zatwierdzeniu transakcji ({@link TransactionPhase#AFTER_COMMIT}).
 * <p>
 * Dodatkowo {@code fallbackExecution} ustawione jest na {@code true}, co umożliwia
 * wykonanie listenera również poza transakcją (np. w testach lub przy braku transakcji).
 * </p>
 *
 * <pre>
 * Przykład użycia:
 * {@code
 * @DomainEventListener
 * public void onEvent(SomeDomainEvent event) {
 *     // logika reagowania na zdarzenie
 * }
 * }
 * </pre>
 *
 * @see TransactionalEventListener
 * @see TransactionPhase
 */
@TransactionalEventListener
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface DomainEventListener {

    /**
     * Faza transakcji, w której listener ma być wywołany.
     * Domyślnie: {@link TransactionPhase#AFTER_COMMIT}.
     *
     * @return faza transakcji
     */
    @AliasFor(annotation = TransactionalEventListener.class, attribute = "phase")
    TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

    /**
     * Czy listener może być wywołany także poza transakcją.
     * Domyślnie: {@code true}, co ułatwia testowanie i użycie poza kontekstem transakcyjnym.
     *
     * @return czy zezwalać na fallback poza transakcją
     */
    @AliasFor(annotation = TransactionalEventListener.class, attribute = "fallbackExecution")
    boolean fallbackExecution() default true;

}
