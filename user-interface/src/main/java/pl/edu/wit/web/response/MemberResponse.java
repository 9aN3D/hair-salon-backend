package pl.edu.wit.web.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.member.dto.MemberDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class MemberResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotBlank
    String surname;

    @NotBlank
    String phone;

    @NotNull
    AuthDetailsResponse authDetails;

    @NotNull
    LocalDateTime registrationDateTime;

    public String getFullName() {
        return name + " " + surname;
    }

    public static MemberResponse of(MemberDto dto) {
        return MemberResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .phone(dto.getPhoneNumber())
                .authDetails(AuthDetailsResponse.of(dto.getAuthDetails()))
                .registrationDateTime(dto.getRegistrationDateTime())
                .build();
    }

}
