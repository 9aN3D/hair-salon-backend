package pl.edu.wit.auth.details.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;

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
