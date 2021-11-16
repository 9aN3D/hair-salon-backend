package pl.edu.wit.hairsalon.authDetails;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
class AuthDetails implements SelfValidator<AuthDetails> {

    private final String id;
    private final Email email;
    private final Password password;
    private final AuthDetailsStatus status;
    private final AuthDetailsRole role;

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

}
