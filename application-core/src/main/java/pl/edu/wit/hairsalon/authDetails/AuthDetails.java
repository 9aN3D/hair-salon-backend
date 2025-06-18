package pl.edu.wit.hairsalon.authDetails;

import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.Objects;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

record AuthDetails(
        String id,
        Email email,
        Password password,
        AuthDetailsStatus status,
        AuthDetailsRole role
) implements SelfValidator<AuthDetails> {

    @Override
    public AuthDetails validate() {
        requireNonNull(email, "Auth details email must not be null");
        requireNonNull(password, "Auth details password must not be null");
        requireNonNull(status, "Auth details status must not be null");
        requireNonNull(role, "Auth details role must not be null");
        validate(new NotBlankString(id));
        return this;
    }

    public AuthDetailsDto toDto() {
        return AuthDetailsDto.builder()
                .id(id)
                .email(email.value())
                .password(password.value())
                .status(status.toDto())
                .role(role.toDto())
                .build();
    }

    AuthDetails encodePassword(PasswordEncoderPort passwordEncoderPort) {
        if (nonNull(password)) {
            var encodedPassword = password.encode(passwordEncoderPort);
            return copy(encodedPassword);
        }
        return this;
    }

    AuthDetails copy(Password password) {
        return AuthDetails.builder()
                .id(id)
                .email(email)
                .password(password)
                .status(status)
                .role(role)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthDetails that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private Email email;
        private Password password;
        private AuthDetailsStatus status;
        private AuthDetailsRole role;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder status(AuthDetailsStatus status) {
            this.status = status;
            return this;
        }

        public Builder role(AuthDetailsRole role) {
            this.role = role;
            return this;
        }

        public AuthDetails build() {
            return new AuthDetails(id, email, password, status, role);
        }

    }

}
