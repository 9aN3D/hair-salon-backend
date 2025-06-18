package pl.edu.wit.hairsalon.hairdresser.query;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record HairdresserFindQuery(
        String id,
        String fullName
) {

    public static HairdresserFindQuery ofHairdresserId(String id) {
        return new HairdresserFindQuery(id, null);
    }

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    public void ifFullNamePresent(Consumer<String> action) {
        if (nonNull(fullName) && !fullName.isBlank()) {
            action.accept(fullName);
        }
    }

    public static HairdresserFindQuery empty() {
        return new HairdresserFindQuery(null, null);
    }

}
