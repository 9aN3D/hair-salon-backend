package pl.edu.wit.application.domain.model.auth_details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.command.AuthDetailsUpdateCommand;
import pl.edu.wit.application.domain.model.Email;
import pl.edu.wit.application.domain.model.Password;
import pl.edu.wit.application.dto.AuthDetailsDto;
import pl.edu.wit.application.exception.auth_details.AuthDetailsNotValidException;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private Email email;
    private Password password;
    private AuthDetailsStatus status;
    private AuthDetailsRole role;

    public AuthDetails(String id,
                       Email email,
                       Password password,
                       AuthDetailsStatus status,
                       AuthDetailsRole role) {
        this.id = setId(id);
        this.email = setEmail(email);
        this.password = setPassword(password);
        this.status = setStatus(status);
        this.role = setRole(role);
    }

    public String getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public AuthDetailsStatus getStatus() {
        return status;
    }

    public AuthDetailsRole getRole() {
        return role;
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

    public void update(AuthDetailsUpdateCommand command, Password password) {
        email = ofNullable(command.getEmail()).map(Email::new).orElse(email);
        status = ofNullable(command.getStatus()).orElse(status);
        role = ofNullable(command.getRole()).orElse(role);
        this.password = ofNullable(password).orElse(this.password);
    }

    private String setId(String id) {
        if (nonNull(id)) {
            return id;
        }
        throw new AuthDetailsNotValidException("Auth details id cannot be null");
    }

    private Email setEmail(Email email) {
        if (nonNull(email)) {
            return email;
        }
        throw new AuthDetailsNotValidException("Auth details email cannot be null");
    }

    private Password setPassword(Password password) {
        if (nonNull(password)) {
            return password;
        }
        throw new AuthDetailsNotValidException("Auth details password cannot be null");
    }

    private AuthDetailsStatus setStatus(AuthDetailsStatus authDetailsStatus) {
        if (nonNull(authDetailsStatus)) {
            return authDetailsStatus;
        }
        throw new AuthDetailsNotValidException("Auth details status cannot be null");
    }

    private AuthDetailsRole setRole(AuthDetailsRole authDetailsRole) {
        if (nonNull(authDetailsRole)) {
            return authDetailsRole;
        }
        throw new AuthDetailsNotValidException("Auth details role cannot be null");
    }

}
