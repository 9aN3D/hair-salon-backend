package pl.edu.wit.hairsalon.member;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.member.dto.MemberAgreementDto;
import pl.edu.wit.hairsalon.sharedKernel.CollectionHelper;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static pl.edu.wit.hairsalon.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.hairsalon.member.MemberAgreement.RESERVATION_RECEIPT;

@EqualsAndHashCode
@RequiredArgsConstructor
class MemberAgreements implements SelfValidator<MemberAgreements> {

    private final Set<MemberAgreement> agreements;

    @Override
    public MemberAgreements validate() {
        CollectionHelper.requireNotNullOrEmpty(agreements, "Member agreements must not be null or empty");
        if (hasNotRequiredAgreements(agreements)) {
            throw new ValidationException("Personal data agreement and reservation receipt agreement required");
        }
        return this;
    }

    Set<MemberAgreement> values() {
        return agreements;
    }

    Set<MemberAgreementDto> toDto() {
        return agreements.stream()
                .map(MemberAgreement::toDto)
                .collect(toSet());
    }

    static MemberAgreements of(Set<MemberAgreementDto> agreements) {
        return new MemberAgreements(
                agreements.stream()
                        .map(Enum::name)
                        .map(MemberAgreement::valueOf)
                        .collect(Collectors.toSet())
        );
    }

    private boolean hasNotRequiredAgreements(Set<MemberAgreement> values) {
        return !hasRequiredAgreements(values);
    }

    private boolean hasRequiredAgreements(Set<MemberAgreement> values) {
        return values.containsAll(EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT));
    }

}
