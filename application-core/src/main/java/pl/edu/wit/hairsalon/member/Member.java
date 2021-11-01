package pl.edu.wit.hairsalon.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Member implements SelfValidator<Member> {

    private final String id;
    private final MemberFullName fullName;
    private final MemberContact contact;
    private final MemberAgreements agreements;
    private final LocalDateTime registrationDateTime;

    @Override
    public Member validate() {
        requireNonNull(fullName, "Member full name must not be null");
        requireNonNull(contact, "Member contact must not be null");
        requireNonNull(agreements, "Member agreements must not be null");
        requireNonNull(registrationDateTime, "Member registration date time must not be null");
        validate(new NotBlankString(id), fullName, contact, agreements);
        return this;
    }

    Member verifyContactPhoneNumber(PhoneNumberPort port) {
        contact.verifyPhoneNumber(port);
        return this;
    }

    MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .fullName(fullName.toDto())
                .contact(contact.toDto())
                .agreements(agreements.toDto())
                .registrationDateTime(registrationDateTime)
                .build();
    }

}
