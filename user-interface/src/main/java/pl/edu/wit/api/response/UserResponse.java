package pl.edu.wit.api.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.application.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class UserResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotBlank
    String surname;

    @NotNull
    AuthDetailsResponse authDetails;

    public static UserResponse of(UserDto dto) {
        return UserResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .authDetails(AuthDetailsResponse.of(dto.getAuthDetails()))
                .build();
    }

}
