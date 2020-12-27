package pl.edu.wit.auth_details.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.auth_details.shared.Role;
import pl.edu.wit.auth_details.shared.command.CreateAuthDetailsCommand;
import pl.edu.wit.auth_details.shared.dto.AuthDetailsDto;
import pl.edu.wit.auth_details.shared.exception.AuthDetailsRoleNotValidException;

import java.util.Set;

import static pl.edu.wit.common.CollectionHelper.nonEmpty;

@ToString
@EqualsAndHashCode(of = "id")
public class AuthDetails {

    private final String id;
    private final Email email;
    private final AuthDetailsPassword password;
    private final Set<Role> roles;

    public AuthDetails(IdGenerator idGenerator, String email, String password, Set<Role> roles) {
        this.id = idGenerator.generate();
        this.email = new Email(email);
        this.password = new AuthDetailsPassword(password);
        this.roles = validateRoles(roles);
    }

    public AuthDetails(IdGenerator idGenerator, CreateAuthDetailsCommand command) {
        this(idGenerator, command.getEmail(), command.getPassword(), command.getRoles());
    }

    public AuthDetailsDto toDto() {
        return AuthDetailsDto.builder()
                .id(id)
                .email(email.value())
                .password(password.value())
                .build();
    }

    private Set<Role> validateRoles(Set<Role> set) {
        if (nonEmpty(set)) {
            return set;
        }
        throw new AuthDetailsRoleNotValidException("Auth details role collections cannot be null or empty");
    }

}
