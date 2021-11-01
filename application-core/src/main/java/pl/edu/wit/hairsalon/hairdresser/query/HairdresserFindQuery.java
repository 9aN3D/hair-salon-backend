package pl.edu.wit.hairsalon.hairdresser.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HairdresserFindQuery {

    private String id;

    private String fullName;

    public static HairdresserFindQuery ofHairdresserId(String id) {
        return HairdresserFindQuery.builder()
                .id(id)
                .build();
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

}
