package pl.edu.wit.auth_details.shared.dto;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.auth_details.shared.AuthDetailsRole;
import pl.edu.wit.auth_details.shared.AuthDetailsStatus;

@Value
@Builder
public class AuthDetailsDto {

    String id;

    String email;

    String password;

    AuthDetailsStatus authDetailsStatus;

    AuthDetailsRole authDetailsRole;

}
