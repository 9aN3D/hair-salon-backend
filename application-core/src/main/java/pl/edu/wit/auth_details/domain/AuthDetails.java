package pl.edu.wit.auth_details.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.PasswordEncoder;
import pl.edu.wit.auth_details.shared.AuthDetailsRole;
import pl.edu.wit.auth_details.shared.AuthDetailsStatus;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsNotValidException;

import static java.util.Objects.nonNull;

@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private final Email email;
    private final AuthDetailsPassword password;
    private final AuthDetailsStatus authDetailsStatus;
    private final AuthDetailsRole authDetailsRole;

    public AuthDetails(IdGenerator idGenerator,
                       PasswordEncoder passwordEncoder,
                       String email,
                       String password,
                       AuthDetailsStatus authDetailsStatus,
                       AuthDetailsRole authDetailsRole) {
        this.id = idGenerator.generate();
        this.email = new Email(email);
        this.password = new AuthDetailsPassword(password, passwordEncoder);
        this.authDetailsStatus = validateStatus(authDetailsStatus);
        this.authDetailsRole = validateRole(authDetailsRole);
    }

    public AuthDetails(IdGenerator idGenerator, PasswordEncoder passwordEncoder, CreateAuthDetailsCommand command) {
        this(idGenerator, passwordEncoder, command.getEmail(), command.getPassword(), command.getAuthDetailsStatus(), command.getAuthDetailsRole());
    }

    private AuthDetailsStatus validateStatus(AuthDetailsStatus authDetailsStatus) {
        if (nonNull(authDetailsStatus)) {
            return authDetailsStatus;
        }
        throw new AuthDetailsNotValidException("Auth details status cannot be null");
    }

    private AuthDetailsRole validateRole(AuthDetailsRole authDetailsRole) {
        if (nonNull(authDetailsRole)) {
            return authDetailsRole;
        }
        throw new AuthDetailsNotValidException("Auth details role cannot be null");
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

    public AuthDetailsStatus getAuthDetailsStatus() {
        return authDetailsStatus;
    }

    public AuthDetailsRole getAuthDetailsRole() {
        return authDetailsRole;
    }

}
