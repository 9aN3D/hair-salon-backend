package pl.edu.wit.hairsalon.user.query;

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
public class UserFindQuery {

    private String id;

    private String fullName;

    public static UserFindQuery withId(String userId) {
        return UserFindQuery.builder()
                .id(userId)
                .build();
    }

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id) && !id.isBlank()) {
            action.accept(id);
        }
    }

    public void ifFullNamePresent(Consumer<String> action) {
        if (nonNull(fullName) && !fullName.trim().isBlank()) {
            action.accept(fullName);
        }
    }

}
