package pl.edu.wit.auth_details.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.PasswordEncoder;
import pl.edu.wit.auth_details.shared.Role;
import pl.edu.wit.auth_details.shared.Status;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsRoleNotValidException;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsStatusNotValidException;

import static java.util.Objects.nonNull;

@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private final Email email;
    private final AuthDetailsPassword password;
    private final Status status;
    private final Role role;

    public AuthDetails(IdGenerator idGenerator,
                       PasswordEncoder passwordEncoder,
                       String email,
                       String password,
                       Status status,
                       Role role) {
        this.id = idGenerator.generate();
        this.email = new Email(email);
        this.password = new AuthDetailsPassword(password, passwordEncoder);
        this.status = validateStatus(status);
        this.role = validateRole(role);
    }

    public AuthDetails(IdGenerator idGenerator, PasswordEncoder passwordEncoder, CreateAuthDetailsCommand command) {
        this(idGenerator, passwordEncoder, command.getEmail(), command.getPassword(), command.getStatus(), command.getRole());
    }

    private Status validateStatus(Status status) {
        if (nonNull(status)) {
            return status;
        }
        throw new AuthDetailsStatusNotValidException("Auth details role collections cannot be null");
    }

    private Role validateRole(Role role) {
        if (nonNull(role)) {
            return role;
        }
        throw new AuthDetailsRoleNotValidException("Auth details role collections cannot be null");
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

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

}
