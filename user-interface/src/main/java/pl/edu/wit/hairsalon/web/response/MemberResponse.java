package pl.edu.wit.hairsalon.web.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.dto.MemberFullNameDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class MemberResponse {

    @NotBlank
    String id;

    @NotBlank
    MemberFullNameDto fullName;

    @NotBlank
    MemberContactDto contact;

    @NotNull
    LocalDateTime registrationDateTime;

    public static MemberResponse of(MemberDto dto) {
        return MemberResponse.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .contact(dto.getContact())
                .registrationDateTime(dto.getRegistrationDateTime())
                .build();
    }

}
