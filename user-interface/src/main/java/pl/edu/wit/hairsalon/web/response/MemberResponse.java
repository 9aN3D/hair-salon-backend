package pl.edu.wit.hairsalon.web.response;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.member.dto.MemberFullNameDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
                .id(dto.id())
                .fullName(dto.fullName())
                .contact(dto.contact())
                .registrationDateTime(dto.registrationDateTime())
                .build();
    }

}
