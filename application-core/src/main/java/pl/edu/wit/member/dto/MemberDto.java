package pl.edu.wit.member.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.member.domain.MemberAgreement;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class MemberDto {

    String id;

    String name;

    String surname;

    String phoneNumber;

    Set<MemberAgreement> agreements;

    AuthDetailsDto authDetails;

    LocalDateTime registrationDateTime;

}
