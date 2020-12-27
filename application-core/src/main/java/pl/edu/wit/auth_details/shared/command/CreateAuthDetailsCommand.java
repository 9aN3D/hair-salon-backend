package pl.edu.wit.auth_details.shared.command;

import lombok.Data;
import pl.edu.wit.auth_details.shared.Role;

import java.util.Set;

@Data
public class CreateAuthDetailsCommand {

    private String email;

    private String password;

    private Set<Role> roles;

}
