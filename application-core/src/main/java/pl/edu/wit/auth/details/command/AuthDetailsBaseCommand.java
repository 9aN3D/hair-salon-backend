package pl.edu.wit.auth.details.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetailsBaseCommand {

    private String email;

    private String password;

    private AuthDetailsRole role;

    private AuthDetailsStatus status;

}
