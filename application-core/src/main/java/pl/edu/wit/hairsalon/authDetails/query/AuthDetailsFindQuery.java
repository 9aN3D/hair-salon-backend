package pl.edu.wit.hairsalon.authDetails.query;

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
