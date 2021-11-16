package pl.edu.wit.hairsalon.authDetails.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuthDetailsCreateCommand extends AuthDetailsBaseCommand {

    private AuthDetailsRoleDto role;

    @Builder
    public AuthDetailsCreateCommand(String email, String password, AuthDetailsStatusDto status, AuthDetailsRoleDto role) {
        super(email, password, status);
        this.role = role;
    }

}
