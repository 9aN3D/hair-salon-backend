package pl.edu.wit.hairsalon.eventbus;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TransactionalEventListener
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface DomainEventListener {

    @AliasFor(annotation = TransactionalEventListener.class, attribute = "phase")
    TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

    @AliasFor(annotation = TransactionalEventListener.class, attribute = "fallbackExecution")
    boolean fallbackExecution() default true;

}
