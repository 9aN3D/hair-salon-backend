package pl.edu.wit.hairsalon.authDetails.command;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

import java.util.StringJoiner;

public record AuthDetailsCreateCommand(
        String email,
        String password,
        AuthDetailsStatusDto status,
        AuthDetailsRoleDto role
) {

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthDetailsCreateCommand.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("status=" + status)
                .add("role=" + role)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;
        private AuthDetailsStatusDto status;
        private AuthDetailsRoleDto role;

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

        public AuthDetailsCreateCommand build() {
            return new AuthDetailsCreateCommand(
                    email,
                    password,
                    status,
                    role
            );
        }
    }
    
}
