package pl.edu.wit.hairsalon.user.query;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record UserFindQuery(
        String id,
        String fullName
) {

    public static UserFindQuery fullName(String fullName) {
        return new UserFindQuery(null, fullName);
    }

    public static UserFindQuery withId(String userId) {
        return new UserFindQuery(userId, null);
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
