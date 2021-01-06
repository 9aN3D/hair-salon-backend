package pl.edu.wit.domain.model.member;

import lombok.EqualsAndHashCode;
import pl.edu.wit.domain.exception.member.MemberAgreementsNotValid;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static pl.edu.wit.domain.model.member.MemberAgreement.MARKETING;
import static pl.edu.wit.domain.model.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.domain.model.member.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
public class MemberAgreements {

    private final Set<MemberAgreement> agreements;

    public MemberAgreements(Boolean personalDataAgreement,
                            Boolean reservationReceiptAgreement,
                            Boolean marketingAgreement) {
        var agreements = EnumSet.noneOf(MemberAgreement.class);
        addPersonalDataAgreement(agreements, personalDataAgreement);
        addReservationReceiptAgreement(agreements, reservationReceiptAgreement);
        addMarketingAgreement(agreements, marketingAgreement);
        this.agreements = agreements;
    }

    public Set<MemberAgreement> value() {
        return agreements;
    }

    public static MemberAgreementsBuilder builder() {
        return new MemberAgreementsBuilder();
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

    public static class MemberAgreementsBuilder {

        private Boolean personalDataAgreement;
        private Boolean reservationReceiptAgreement;
        private Boolean marketingAgreement;

        MemberAgreementsBuilder() {
        }

        public MemberAgreements.MemberAgreementsBuilder personalDataAgreement(Boolean personalDataAgreement) {
            this.personalDataAgreement = personalDataAgreement;
            return this;
        }

        public MemberAgreements.MemberAgreementsBuilder reservationReceiptAgreement(Boolean reservationReceiptAgreement) {
            this.reservationReceiptAgreement = reservationReceiptAgreement;
            return this;
        }

        public MemberAgreements.MemberAgreementsBuilder marketingAgreement(Boolean marketingAgreement) {
            this.marketingAgreement = marketingAgreement;
            return this;
        }

        public MemberAgreements build() {
            return new MemberAgreements(personalDataAgreement, reservationReceiptAgreement, marketingAgreement);
        }

        public String toString() {
            return "MemberAgreements.MemberAgreementsBuilder(" +
                    "personalDataAgreement=" + this.personalDataAgreement +
                    ", reservationReceiptAgreement=" + this.reservationReceiptAgreement +
                    ", marketingAgreement=" + this.marketingAgreement +
                    ")";
        }

    }

}
