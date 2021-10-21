package pl.edu.wit.application.domain.model.member;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.exception.ValidationException;

import java.util.EnumSet;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.application.domain.model.member.MemberAgreement.RESERVATION_RECEIPT;

class MemberAgreementsTest {

    @Test
    void shouldCreateMemberAgreements() {
        var agreements = EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT);

        var memberAgreements = new MemberAgreements(agreements);

        assertEquals(agreements.size(), memberAgreements.values().size());
        assertTrue(memberAgreements.values().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(memberAgreements.values().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new MemberAgreements(null);
        });
        assertEquals("Personal data agreement and reservation receipt agreement required also can not be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsEmptyCollection() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new MemberAgreements(new HashSet<>());
        });
        assertEquals("Personal data agreement and reservation receipt agreement required also can not be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueHasPersonalDataAgreement() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new MemberAgreements(EnumSet.of(PERSONAL_DATA));
        });
        assertEquals("Personal data agreement and reservation receipt agreement required also can not be null", validationException.getMessage());
    }

}
