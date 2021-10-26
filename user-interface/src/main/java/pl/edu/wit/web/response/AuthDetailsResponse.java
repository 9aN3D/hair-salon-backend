package pl.edu.wit.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;

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
    AuthDetailsStatus status;

    @NotNull
    AuthDetailsRole role;

    public static AuthDetailsResponse of(AuthDetailsDto dto) {
        return AuthDetailsResponse.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .status(dto.getStatus())
                .role(dto.getRole())
                .build();
    }

}
