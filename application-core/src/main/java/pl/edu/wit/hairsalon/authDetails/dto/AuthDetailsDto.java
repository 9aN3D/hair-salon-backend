package pl.edu.wit.hairsalon.authDetails.dto;

import java.util.Objects;
import java.util.StringJoiner;

public record AuthDetailsDto(
        String id,
        String email,
        String password,
        AuthDetailsStatusDto status,
        AuthDetailsRoleDto role
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String email;
        private String password;
        private AuthDetailsStatusDto status;
        private AuthDetailsRoleDto role;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder status(AuthDetailsStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder role(AuthDetailsRoleDto role) {
            this.role = role;
            return this;
        }

        public AuthDetailsDto build() {
            return new AuthDetailsDto(
                    id,
                    email,
                    password,
                    status,
                    role
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthDetailsDto that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthDetailsDto.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("id='" + id + "'")
                .add("status=" + status)
                .add("role=" + role)
                .toString();
    }

}
