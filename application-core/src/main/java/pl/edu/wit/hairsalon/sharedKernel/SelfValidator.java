package pl.edu.wit.hairsalon.sharedKernel;

import java.util.Arrays;
import java.util.Collection;

public interface SelfValidator<T> {

    T validate();

    default void validate(SelfValidator<?>... validators) {
        Arrays.stream(validators).forEach(SelfValidator::validate);
    }

    default void validate(Collection<? extends SelfValidator<?>> validators) {
        validators.forEach(SelfValidator::validate);
    }

}
