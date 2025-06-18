package pl.edu.wit.hairsalon.member.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.time.LocalDateTime;
import java.util.Set;

public record MemberDto(String id,
                        FullNameDto fullName,
                        MemberContactDto contact,
                        Set<MemberAgreementDto> agreements,
                        LocalDateTime registrationDateTime) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private MemberContactDto contact;
        private Set<MemberAgreementDto> agreements;
        private LocalDateTime registrationDateTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder contact(MemberContactDto contact) {
            this.contact = contact;
            return this;
        }

        public Builder agreements(Set<MemberAgreementDto> agreements) {
            this.agreements = agreements;
            return this;
        }

        public Builder registrationDateTime(LocalDateTime registrationDateTime) {
            this.registrationDateTime = registrationDateTime;
            return this;
        }

        public MemberDto build() {
            return new MemberDto(id, fullName, contact, agreements, registrationDateTime);
        }

    }

}
