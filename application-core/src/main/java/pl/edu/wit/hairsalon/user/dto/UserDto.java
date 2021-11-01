package pl.edu.wit.hairsalon.user.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class UserDto {

    String id;

    UserFullNameDto fullName;

    UserContactDto contact;

    LocalDateTime registrationDateTime;

}
