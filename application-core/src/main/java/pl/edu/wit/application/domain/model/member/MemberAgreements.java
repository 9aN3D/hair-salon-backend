package pl.edu.wit.application.domain.model.member;

import lombok.EqualsAndHashCode;
import pl.edu.wit.application.exception.member.MemberAgreementsNotValidException;

import java.util.EnumSet;
import java.util.Set;

import static pl.edu.wit.application.common.CollectionHelper.isEmpty;
import static pl.edu.wit.application.common.CollectionHelper.nonEmpty;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
public class MemberAgreements {

    private final Set<MemberAgreement> agreements;

    public MemberAgreements(Set<MemberAgreement> agreements) {
        this.agreements = setAgreements(agreements);
    }

    public Set<MemberAgreement> value() {
        return agreements;
    }

    public void update(Set<MemberAgreement> agreements) {
        if (nonEmpty(agreements)) {
            this.agreements.addAll(agreements);
        }
    }

    private Set<MemberAgreement> setAgreements(Set<MemberAgreement> agreements) {
        if (isEmpty(agreements) && !agreements.containsAll(EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT))) {
            throw new MemberAgreementsNotValidException("Personal data agreement and reservation receipt agreement required also can not be null");
        }
        return agreements;
    }

}
