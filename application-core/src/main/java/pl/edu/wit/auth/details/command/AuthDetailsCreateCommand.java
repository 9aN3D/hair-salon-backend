package pl.edu.wit.auth.details.command;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuthDetailsCreateCommand extends AuthDetailsBaseCommand {

    @Builder
    public AuthDetailsCreateCommand(String email, String password, AuthDetailsRole role, AuthDetailsStatus status) {
        super(email, password, role, status);
    }

}
