package pl.edu.wit.user.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class UserDto {

    String id;

    String name;

    String surname;

    AuthDetailsDto authDetails;

    LocalDateTime registrationDateTime;

}
