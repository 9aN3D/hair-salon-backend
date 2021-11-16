package pl.edu.wit.hairsalon.member;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.Objects;

@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class MemberContact implements SelfValidator<MemberContact> {

    private final String email;
    private final PhoneNumber phoneNumber;

    @Override
    public MemberContact validate() {
        validate(new NotBlankString(email));
        Objects.requireNonNull(phoneNumber, "Member contact phone number must be not null");
        phoneNumber.validate();
        return this;
    }

    MemberContact verifyPhoneNumber(PhoneNumberPort port) {
        phoneNumber.verifyPhoneNumber(port);
        return this;
    }

    MemberContactDto toDto() {
        return MemberContactDto.builder()
                .email(email)
                .phone(phoneNumber.value())
                .build();
    }

}
