package pl.edu.wit.auth_details.shared.command;

import lombok.Data;
import pl.edu.wit.auth_details.shared.Role;
import pl.edu.wit.auth_details.shared.Status;

@Data
public class CreateAuthDetailsCommand {

    private String email;

    private String password;

    private Role role;

    private Status status;

}
