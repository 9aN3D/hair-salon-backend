package pl.edu.wit.member.domain;

import lombok.EqualsAndHashCode;
import pl.edu.wit.member.shared.command.RegisterMemberCommand;
import pl.edu.wit.member.shared.exception.MemberAgreementsNotValid;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static pl.edu.wit.member.domain.MemberAgreement.MARKETING;
import static pl.edu.wit.member.domain.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.member.domain.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
public class MemberAgreements {

    private final Set<MemberAgreement> agreements;

    public MemberAgreements(RegisterMemberCommand command) {
        this.agreements = toMemberAgreements(command);
    }

    public Set<MemberAgreement> value() {
        return agreements;
    }

    private Set<MemberAgreement> toMemberAgreements(RegisterMemberCommand command) {
        var memberAgreements = EnumSet.noneOf(MemberAgreement.class);
        addPersonalDataAgreement(memberAgreements, command.getPersonalDataAgreement());
        addReservationReceiptAgreement(memberAgreements, command.getPersonalDataAgreement());
        addMarketingAgreement(memberAgreements, command.getMarketingAgreement());
        return memberAgreements;
    }

    private void addPersonalDataAgreement(EnumSet<MemberAgreement> agreements, Boolean personalDataAgreement) {
        if (isNull(personalDataAgreement) || !personalDataAgreement) {
            throw new MemberAgreementsNotValid("Personal data agreement required and can not be null");
        }
        agreements.add(PERSONAL_DATA);
    }

    private void addReservationReceiptAgreement(EnumSet<MemberAgreement> agreements, Boolean reservationReceiptAgreement) {
        if (isNull(reservationReceiptAgreement) || !reservationReceiptAgreement) {
            throw new MemberAgreementsNotValid("Reservation receipt agreement required and can not be null");
        }
        agreements.add(RESERVATION_RECEIPT);
    }

    private void addMarketingAgreement(EnumSet<MemberAgreement> agreements, Boolean marketingAgreement) {
        if (marketingAgreement) {
            agreements.add(MARKETING);
        }
    }

}
