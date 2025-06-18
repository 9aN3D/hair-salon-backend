package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.Objects;

record MemberContact(String email, PhoneNumber phoneNumber) implements SelfValidator<MemberContact> {

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
        return new MemberContactDto(email, phoneNumber.value());
    }

}
