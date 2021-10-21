package pl.edu.wit.application.command.auth_details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsRole;
import pl.edu.wit.application.domain.model.auth_details.AuthDetailsStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetailsBaseCommand {

    private String email;

    private String password;

    private AuthDetailsRole role;

    private AuthDetailsStatus status;

}
