package pl.edu.wit.domain.common;

import static java.util.Objects.isNull;

public class StringNotBlank {

    private final String value;

    public StringNotBlank(String value) {
        this.value = value;
    }

    public String validate() {
        if (isNull(value) || value.isBlank()) {
            throw new StringNotBlankException("String cannot be null or blank");
        }
        return value;
    }

}
