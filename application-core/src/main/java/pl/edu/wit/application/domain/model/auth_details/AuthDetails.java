package pl.edu.wit.application.domain.model.auth_details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.command.auth_details.AuthDetailsUpdateCommand;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.EncodedPassword;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.domain.model.Password;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.exception.ValidationException;
import pl.edu.wit.application.port.secondary.PasswordEncoder;

import static java.util.Optional.ofNullable;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private final Email email;
    private final Password password;
    private final AuthDetailsStatus status;
    private final AuthDetailsRole role;

    public AuthDetails(String id, Email email, Password password, AuthDetailsStatus status, AuthDetailsRole role) {
        this.id = new NotBlankString(id).value();
        this.email = ofNullable(email)
                .orElseThrow(() -> new ValidationException("Auth details email cannot be null"));
        this.password = ofNullable(password)
                .orElseThrow(() -> new ValidationException("Auth details password cannot be null"));
        this.status = ofNullable(status)
                .orElseThrow(() -> new ValidationException("Auth details status cannot be null"));
        this.role = ofNullable(role)
                .orElseThrow(() -> new ValidationException("Auth details role cannot be null"));
    }

    public AuthDetails(AuthDetailsDto dto) {
        this(
                dto.getId(),
                new Email(dto.getEmail()),
                new EncodedPassword(dto.getPassword()),
                dto.getStatus(),
                dto.getRole()
        );
    }

    public AuthDetailsDto toDto() {
        return AuthDetailsDto.builder()
                .id(id)
                .email(email.value())
                .password(password.value())
                .status(status)
                .role(role)
                .build();
    }

    public AuthDetails update(AuthDetailsUpdateCommand command, PasswordEncoder passwordEncoder) {
        return AuthDetails.builder()
                .id(id)
                .email(ofNullable(command.getEmail()).map(Email::new).orElse(email))
                .password(ofNullable(command.getPassword()).map(password -> preparePassword(password, passwordEncoder)).orElse(password))
                .status(ofNullable(command.getStatus()).orElse(status))
                .role(ofNullable(command.getRole()).orElse(role))
                .build();
    }

    public String id() {
        return id;
    }

    private Password preparePassword(String password, PasswordEncoder passwordEncoder) {
        return new EncodedPassword(new AuthDetailsPassword(password), passwordEncoder);
    }

}
