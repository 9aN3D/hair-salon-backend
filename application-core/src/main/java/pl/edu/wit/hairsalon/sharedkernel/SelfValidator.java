package pl.edu.wit.hairsalon.sharedkernel;

import java.util.Arrays;

public interface SelfValidator<T> {

    T validate();

    default void validate(SelfValidator<?>... validators) {
        Arrays.stream(validators).forEach(SelfValidator::validate);
    }

}
