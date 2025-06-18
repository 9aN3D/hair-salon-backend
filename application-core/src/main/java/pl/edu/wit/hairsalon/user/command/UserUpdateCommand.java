package pl.edu.wit.hairsalon.user.command;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsUpdateCommand;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import static java.util.Objects.nonNull;

public record UserUpdateCommand(
        String name,
        String surname,
        String email,
        String password
) {

    public AuthDetailsUpdateCommand toAuthDetailsUpdateCommand() {
        return AuthDetailsUpdateCommand.builder()
                .email(email)
                .password(password)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isNotBlankEmail() {
        return nonNull(email) && !email.isBlank();
    }

    public boolean isNotBlankPassword() {
        return nonNull(password) && !password.isBlank();
    }

    public static class Builder {

        private String name;
        private String surname;
        private String email;
        private String password;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
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

        public UserCreateCommand build() {
            return new UserCreateCommand(name, surname, email, password);
        }

    }

}
