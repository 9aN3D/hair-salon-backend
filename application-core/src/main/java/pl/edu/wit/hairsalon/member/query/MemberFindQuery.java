package pl.edu.wit.hairsalon.member.query;

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
public class MemberFindQuery {

    private String id;

    private String fullName;

    private String phone;

    public static MemberFindQuery withId(String id) {
        return MemberFindQuery.builder()
                .id(id)
                .build();
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
