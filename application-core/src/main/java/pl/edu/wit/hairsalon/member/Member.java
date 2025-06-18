package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.FullName;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

record Member(
        String id,
        FullName fullName,
        MemberContact contact,
        MemberAgreements agreements,
        LocalDateTime registrationDateTime
) implements SelfValidator<Member> {

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

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private FullName fullName;
        private MemberContact contact;
        private MemberAgreements agreements;
        private LocalDateTime registrationDateTime;

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder fullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        Builder contact(MemberContact contact) {
            this.contact = contact;
            return this;
        }

        Builder agreements(MemberAgreements agreements) {
            this.agreements = agreements;
            return this;
        }

        Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        Member build() {
            return new Member(id, fullName, contact, agreements, registrationDateTime);
        }

    }

}
