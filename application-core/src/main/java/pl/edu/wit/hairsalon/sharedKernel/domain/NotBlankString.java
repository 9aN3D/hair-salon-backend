package pl.edu.wit.hairsalon.sharedKernel.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class NotBlankString implements SelfValidator<NotBlankString> {

    private final String value;

    public String value() {
        return value;
    }

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
