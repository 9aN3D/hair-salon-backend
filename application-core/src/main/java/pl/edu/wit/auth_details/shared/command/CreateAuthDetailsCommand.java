package pl.edu.wit.auth_details.shared.command;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.auth_details.shared.AuthDetailsRole;
import pl.edu.wit.auth_details.shared.AuthDetailsStatus;

@Data
@Builder
@NoArgsConstructor
public class CreateAuthDetailsCommand {

    private String email;

    private String password;

    private AuthDetailsRole authDetailsRole;

    private AuthDetailsStatus authDetailsStatus;

}
