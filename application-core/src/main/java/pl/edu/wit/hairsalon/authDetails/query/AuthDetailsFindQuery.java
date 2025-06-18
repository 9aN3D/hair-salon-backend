package pl.edu.wit.hairsalon.authDetails.query;

import java.util.function.Consumer;

import static java.util.Objects.nonNull;

public record AuthDetailsFindQuery(
        String id,
        String email
) {

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
    @Deprecated
    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    @Deprecated
    public void ifEmailPresent(Consumer<String> action) {
        if (nonNull(email) && !email.isBlank()) {
            action.accept(email);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String email;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public AuthDetailsFindQuery build() {
            return new AuthDetailsFindQuery(id, email);
        }

    }

}
