package pl.edu.wit.application.domain.model.member;

import lombok.EqualsAndHashCode;
import pl.edu.wit.application.exception.ValidationException;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
public class MemberAgreements {

    private final Set<MemberAgreement> agreements;

    public MemberAgreements(Set<MemberAgreement> agreements) {
        this.agreements = ofNullable(agreements)
                .filter(not(Set::isEmpty))
                .filter(this::hasRequiredAgreements)
                .orElseThrow(() -> new ValidationException("Personal data agreement and reservation receipt agreement required also can not be null"));
    }

    public Set<MemberAgreement> values() {
        return agreements;
    }

    private boolean hasNotRequiredAgreements(Set<MemberAgreement> values) {
        return !hasRequiredAgreements(values);
    }

    private boolean hasRequiredAgreements(Set<MemberAgreement> values) {
        return values.containsAll(EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT));
    }

}
