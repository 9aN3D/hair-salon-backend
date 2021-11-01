package pl.edu.wit.hairsalon.member.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class MemberDto {

    String id;

    MemberFullNameDto fullName;

    MemberContactDto contact;

    Set<MemberAgreementDto> agreements;

    LocalDateTime registrationDateTime;

}
