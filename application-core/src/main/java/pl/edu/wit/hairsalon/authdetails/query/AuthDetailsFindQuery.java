package pl.edu.wit.hairsalon.authdetails.query;

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
public class AuthDetailsFindQuery {

    private String id;

    private String email;

    public static AuthDetailsFindQuery withId(String authDetailsId) {
        return AuthDetailsFindQuery.builder()
                .id(authDetailsId)
                .build();
    }

    public static AuthDetailsFindQuery ofEmail(String email) {
        return AuthDetailsFindQuery.builder()
                .email(email)
                .build();
    }

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    public void ifEmailPresent(Consumer<String> action) {
        if (nonNull(email) && !email.isBlank()) {
            action.accept(email);
        }
    }

}
