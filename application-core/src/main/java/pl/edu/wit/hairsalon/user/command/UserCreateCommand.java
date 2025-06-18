package pl.edu.wit.hairsalon.user.command;

import pl.edu.wit.hairsalon.authDetails.command.AuthDetailsCreateCommand;

import java.util.StringJoiner;

import static pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto.ADMIN;

public record UserCreateCommand(
        String name,
        String surname,
        String email,
        String password
) {

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCreateCommand.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("email='" + email + "'")
                .toString();
    }

    public AuthDetailsCreateCommand toAuthDetailsCreateCommand() {
        return AuthDetailsCreateCommand.builder()
                .email(email)
                .password(password)
                .role(ADMIN)
                .build();
    }

    public static Builder builder() {
        return new Builder();
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
