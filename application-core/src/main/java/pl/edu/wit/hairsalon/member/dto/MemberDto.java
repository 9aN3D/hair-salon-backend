package pl.edu.wit.hairsalon.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record MemberDto(String id,
                        MemberFullNameDto fullName,
                        MemberContactDto contact,
                        Set<MemberAgreementDto> agreements,
                        LocalDateTime registrationDateTime) {

}
