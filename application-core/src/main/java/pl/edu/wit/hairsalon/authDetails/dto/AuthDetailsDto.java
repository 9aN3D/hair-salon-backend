package pl.edu.wit.hairsalon.authDetails.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class AuthDetailsDto {

    String id;

    String email;

    String password;

    AuthDetailsStatusDto status;

    AuthDetailsRoleDto role;

}
