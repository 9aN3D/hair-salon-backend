package pl.edu.wit.hairsalon.member.query;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record MemberFindQuery(
        String id,
        String fullName,
        String phone
) {

    public static MemberFindQuery withId(String id) {
        return new MemberFindQuery(id, null, null);
    }

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    public void ifFullNamePresent(Consumer<String> action) {
        if (nonNull(fullName) && !fullName.trim().isBlank()) {
            action.accept(fullName);
        }
    }

    public void ifPhonePresent(Consumer<String> action) {
        if (nonNull(phone) && !phone.trim().isBlank()) {
            action.accept(phone);
        }
    }

}
