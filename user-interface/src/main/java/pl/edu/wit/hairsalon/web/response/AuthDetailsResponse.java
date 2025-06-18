package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

import java.util.Objects;

public record AuthDetailsResponse(
        @NotBlank String id,
        @NotBlank String email,
        @NotNull AuthDetailsStatusDto status,
        @NotNull AuthDetailsRoleDto role
) {

    public static AuthDetailsResponse of(AuthDetailsDto dto) {
        return builder()
                .id(dto.id())
                .email(dto.email())
                .status(dto.status())
                .role(dto.role())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private String email;
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

        public Builder status(AuthDetailsStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder role(AuthDetailsRoleDto role) {
            this.role = role;
            return this;
        }

        public AuthDetailsResponse build() {
            Objects.requireNonNull(id, "id must not be null");
            Objects.requireNonNull(email, "email must not be null");
            Objects.requireNonNull(status, "status must not be null");
            Objects.requireNonNull(role, "role must not be null");
            return new AuthDetailsResponse(id, email, status, role);
        }

    }

}

