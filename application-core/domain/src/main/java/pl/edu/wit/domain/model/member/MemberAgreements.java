package pl.edu.wit.domain.model.member;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.domain.exception.member.MemberAgreementsNotValidException;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Objects.isNull;
import static pl.edu.wit.domain.common.CollectionHelper.isEmpty;
import static pl.edu.wit.domain.model.member.MemberAgreement.MARKETING;
import static pl.edu.wit.domain.model.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.domain.model.member.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
public class MemberAgreements {

    private final Set<MemberAgreement> agreements;

    public MemberAgreements(Set<MemberAgreement> agreements) {
        this.agreements = setAgreements(agreements);
    }

    public Set<MemberAgreement> value() {
        return agreements;
    }

    public static MemberAgreementsBuilder builder() {
        return new MemberAgreementsBuilder();
    }

    private Set<MemberAgreement> setAgreements(Set<MemberAgreement> agreements) {
        if (isEmpty(agreements) && !agreements.containsAll(EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT))) {
            throw new MemberAgreementsNotValidException("Personal data agreement and reservation receipt agreement required also can not be null");
        }
        return agreements;
    }

    @ToString
    public static class MemberAgreementsBuilder {

        private final Set<MemberAgreement> agreements;

        MemberAgreementsBuilder() {
            agreements = EnumSet.noneOf(MemberAgreement.class);
        }

        public MemberAgreements.MemberAgreementsBuilder personalDataAgreement(Boolean personalDataAgreement) {
            addPersonalDataAgreement(agreements, personalDataAgreement);
            return this;
        }

        public MemberAgreements.MemberAgreementsBuilder reservationReceiptAgreement(Boolean reservationReceiptAgreement) {
            addReservationReceiptAgreement(agreements, reservationReceiptAgreement);
            return this;
        }

        public MemberAgreements.MemberAgreementsBuilder marketingAgreement(Boolean marketingAgreement) {
            addMarketingAgreement(agreements, marketingAgreement);
            return this;
        }

        public MemberAgreements build() {
            return new MemberAgreements(agreements);
        }

        private void addPersonalDataAgreement(Set<MemberAgreement> agreements, Boolean personalDataAgreement) {
            if (isNull(personalDataAgreement) || !personalDataAgreement) {
                throw new MemberAgreementsNotValidException("Personal data agreement required and can not be null");
            }
            agreements.add(PERSONAL_DATA);
        }

        private void addReservationReceiptAgreement(Set<MemberAgreement> agreements, Boolean reservationReceiptAgreement) {
            if (isNull(reservationReceiptAgreement) || !reservationReceiptAgreement) {
                throw new MemberAgreementsNotValidException("Reservation receipt agreement required and can not be null");
            }
            agreements.add(RESERVATION_RECEIPT);
        }

        private void addMarketingAgreement(Set<MemberAgreement> agreements, Boolean marketingAgreement) {
            if (marketingAgreement) {
                agreements.add(MARKETING);
            }
        }

    }

}
