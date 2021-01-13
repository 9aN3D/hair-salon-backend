package pl.edu.wit.domain.model.auth_details;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.exception.auth_details.AuthDetailsNotValidException;
import pl.edu.wit.domain.model.Email;

import static java.util.Objects.nonNull;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private final Email email;
    private final AuthDetailsPassword password;
    private final AuthDetailsStatus status;
    private final AuthDetailsRole role;

    public AuthDetails(String id,
                       Email email,
                       AuthDetailsPassword password,
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

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
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
                .email(getEmail())
                .password(getPassword())
                .status(status)
                .role(role)
                .build();
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

    private AuthDetailsPassword setPassword(AuthDetailsPassword password) {
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
