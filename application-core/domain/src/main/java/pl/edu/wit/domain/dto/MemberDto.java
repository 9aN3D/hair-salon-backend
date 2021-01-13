package pl.edu.wit.domain.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.domain.model.member.MemberAgreement;

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
