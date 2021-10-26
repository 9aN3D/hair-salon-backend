package pl.edu.wit.member.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.member.command.MemberUpdateCommand;
import pl.edu.wit.common.domain.Email;
import pl.edu.wit.auth.details.domain.AuthDetails;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.member.dto.MemberDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.port.secondary.PhoneNumberProvider;
import pl.edu.wit.member.domain.Member;
import pl.edu.wit.member.domain.MemberAgreement;
import pl.edu.wit.member.domain.MemberAgreements;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.wit.auth.details.domain.AuthDetailsRole.MEMBER;
import static pl.edu.wit.auth.details.domain.AuthDetailsStatus.ACTIVE;
import static pl.edu.wit.member.domain.MemberAgreement.MARKETING;
import static pl.edu.wit.member.domain.MemberAgreement.PERSONAL_DATA;
import static pl.edu.wit.member.domain.MemberAgreement.RESERVATION_RECEIPT;

class MemberTest {

    private final String id = UUID.randomUUID().toString();
    private final String name = "testName";
    private final String surname = "testSurname";
    private final String phoneNumber = "testPhoneNumber";
    private final Set<MemberAgreement> agreements = EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT);
    private final AuthDetailsDto authDetailsDto = buildAuthDetailsDto();
    private final LocalDateTime registrationDateTime = now();
    private final AuthDetails authDetails = new AuthDetails(authDetailsDto);
    private final MemberAgreements memberAgreements = new MemberAgreements(agreements);
    private final String passwordEncoded = "password_encoded";

    @Test
    void shouldCreateMemberByMemberDto() {
        var memberDto = buildDto();

        var member = new Member(memberDto);
        assertEquals(memberDto, member.toDto());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(null, name, surname, () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member("  ", name, surname, () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, null, surname, () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(null, "  ", surname, () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenSurnameIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, name, null, () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenSurnameIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(null, name, "  ", () -> phoneNumber, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPhoneNumberIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, name, surname, null, memberAgreements, authDetails, registrationDateTime);
        });
        assertEquals("Member phone number cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenAgreementsIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, name, surname, () -> phoneNumber, null, authDetails, registrationDateTime);
        });
        assertEquals("Member agreements cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenAuthDetailsIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, name, surname, () -> phoneNumber, memberAgreements, null, registrationDateTime);
        });
        assertEquals("Member auth details cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenRegistrationDateTimeIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Member(id, name, surname, () -> phoneNumber, memberAgreements, authDetails, null);
        });
        assertEquals("Member registration date time cannot be null", validationException.getMessage());
    }

    @Test
    void shouldUpdateNameMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .name("testName2")
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertNotEquals(memberDto.getName(), updatedMemberDto.getName());
        assertEquals(updateCommand.getName(), updatedMemberDto.getName());
        assertEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldUpdateSurnameMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .surname("testName2")
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertEquals(memberDto.getName(), updatedMemberDto.getName());
        assertNotEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertEquals(updateCommand.getSurname(), updatedMemberDto.getSurname());
        assertEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldUpdatePhoneMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .phone("testPhone")
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertEquals(memberDto.getName(), updatedMemberDto.getName());
        assertEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertNotEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(updateCommand.getPhone(), updatedMemberDto.getPhoneNumber());
        assertEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldUpdateEmailMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .email("test2@gmail.com")
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertEquals(memberDto.getName(), updatedMemberDto.getName());
        assertEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertNotEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(updateCommand.getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldUpdatePasswordMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .password("password_2")
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertEquals(memberDto.getName(), updatedMemberDto.getName());
        assertEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertNotEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertNotEquals(updateCommand.getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(passwordEncoded, updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
    }

    @Test
    void shouldUpdateAgreementsMemberByMemberUpdateCommand() {
        var updateCommand = MemberUpdateCommand.builder()
                .agreements(EnumSet.of(PERSONAL_DATA, RESERVATION_RECEIPT, MARKETING))
                .build();
        var memberDto = buildDto();

        var updatedMember = new Member(memberDto).update(updateCommand, value -> passwordEncoded, getPhoneNumberProvider(true));
        var updatedMemberDto = updatedMember.toDto();

        assertEquals(memberDto.getId(), updatedMemberDto.getId());
        assertEquals(memberDto.getName(), updatedMemberDto.getName());
        assertEquals(memberDto.getSurname(), updatedMemberDto.getSurname());
        assertEquals(memberDto.getPhoneNumber(), updatedMemberDto.getPhoneNumber());
        assertEquals(memberDto.getAuthDetails().getEmail(), updatedMemberDto.getAuthDetails().getEmail());
        assertEquals(memberDto.getAuthDetails().getPassword(), updatedMemberDto.getAuthDetails().getPassword());
        assertEquals(memberDto.getRegistrationDateTime(), updatedMemberDto.getRegistrationDateTime());
        assertNotEquals(memberDto.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertEquals(updateCommand.getAgreements().size(), updatedMemberDto.getAgreements().size());
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == PERSONAL_DATA));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == RESERVATION_RECEIPT));
        assertTrue(updatedMemberDto.getAgreements().stream().anyMatch(agreement -> agreement == MARKETING));
    }

    private MemberDto buildDto() {
        return MemberDto.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .phoneNumber(phoneNumber)
                .authDetails(authDetailsDto)
                .agreements(agreements)
                .registrationDateTime(registrationDateTime)
                .build();
    }

    private AuthDetailsDto buildAuthDetailsDto() {
        return AuthDetailsDto.builder()
                .id(UUID.randomUUID().toString())
                .email(new Email("test@gmail.com").value())
                .password("test_password")
                .role(MEMBER)
                .status(ACTIVE)
                .build();
    }

    private PhoneNumberProvider getPhoneNumberProvider(Boolean value) {
        return new PhoneNumberProvider() {
            @Override
            public Boolean isPossibleNumber(String phoneNumber) {
                return value;
            }

            @Override
            public Boolean isPossibleNumber(String phoneNumber, Locale locale) {
                return value;
            }
        };
    }

}
