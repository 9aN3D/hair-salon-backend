package pl.edu.wit.domain.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import pl.edu.wit.domain.model.auth_details.AuthDetailsRole;
import pl.edu.wit.domain.model.auth_details.AuthDetailsStatus;

@Value
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = "password")
public class AuthDetailsDto {

    String id;

    String email;

    String password;

    AuthDetailsStatus status;

    AuthDetailsRole role;

}
