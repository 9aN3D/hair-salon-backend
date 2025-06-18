package pl.edu.wit.hairsalon.sharedKernel.domain;

import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

public record NotBlankString(String value) implements SelfValidator<NotBlankString> {

    public NotBlankString validate() {
        Objects.requireNonNull(value, "Value must not be null");
        if (value.isBlank()) {
            throw new ValidationException("String must not be null or blank");
        }
        return this;
    }

    public Optional<String> filter(Predicate<String> predicate) {
        return ofNullable(value).filter(predicate);
    }

}
