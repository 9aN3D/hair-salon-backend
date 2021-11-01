package pl.edu.wit.hairsalon.web.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.user.dto.UserContactDto;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.dto.UserFullNameDto;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class UserResponse {

    @NotBlank
    String id;

    @NotBlank
    UserFullNameDto fullName;

    @NotBlank
    UserContactDto contact;

    public static UserResponse of(UserDto dto) {
        return UserResponse.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .contact(dto.getContact())
                .build();
    }

}
