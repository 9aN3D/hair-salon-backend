package pl.edu.wit.hairsalon.member;

import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;

enum MemberAgreement {

    PERSONAL_DATA,
    RESERVATION_RECEIPT,
    MARKETING;

    MemberAgreementDto toDto() {
        return MemberAgreementDto.valueOf(this.name());
    }

}
