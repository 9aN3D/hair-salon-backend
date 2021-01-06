package pl.edu.wit.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.domain.model.auth_details.AuthDetailsRole;
import pl.edu.wit.domain.model.auth_details.AuthDetailsStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthDetailsCommand {

    private String email;

    private String password;

    private AuthDetailsRole role;

    private AuthDetailsStatus status;

}
