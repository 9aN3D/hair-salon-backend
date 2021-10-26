package pl.edu.wit.common.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.common.exception.ValidationException;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@ToString
@EqualsAndHashCode
public class NotBlankString {

    private final String value;

    public NotBlankString(String value) {
        this.value = filterNotBlank(value)
                .orElseThrow(() -> new ValidationException("String cannot be null or blank"));
    }

    public String value() {
        return value;
    }

    public Optional<String> filter(Predicate<String> predicate) {
        return of(value).filter(predicate);
    }

    private Optional<String> filterNotBlank(String value) {
        return ofNullable(value)
                .filter(not(String::isBlank));
    }

}
