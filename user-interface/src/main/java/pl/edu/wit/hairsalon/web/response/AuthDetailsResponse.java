package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor
public class AuthDetailsResponse {

    @NotBlank
    String id;

    @NotBlank
    String email;

    @NotNull
    AuthDetailsStatusDto status;

    @NotNull
    AuthDetailsRoleDto role;

    public static AuthDetailsResponse of(AuthDetailsDto dto) {
        return AuthDetailsResponse.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .status(dto.getStatus())
                .role(dto.getRole())
                .build();
    }

}
