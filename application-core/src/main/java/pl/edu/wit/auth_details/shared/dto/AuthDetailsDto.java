package pl.edu.wit.auth_details.shared.dto;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.auth_details.shared.Role;
import pl.edu.wit.auth_details.shared.Status;

@Value
@Builder
public class AuthDetailsDto {

    String id;

    String email;

    String password;

    Status status;

    Role role;

}
