package pl.edu.wit.hairsalon.authDetails.command;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

public record AuthDetailsUpdateCommand(
        String email,
        String password,
        AuthDetailsStatusDto status
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String password;
        private AuthDetailsStatusDto status;

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

        public AuthDetailsUpdateCommand build() {
            return new AuthDetailsUpdateCommand(
                    email,
                    password,
                    status
            );
        }

    }

}
