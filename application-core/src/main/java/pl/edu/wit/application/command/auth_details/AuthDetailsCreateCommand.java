package pl.edu.wit.application.command.auth_details;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus;

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
