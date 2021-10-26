package pl.edu.wit.auth_details.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.auth.details.command.AuthDetailsUpdateCommand;
import pl.edu.wit.common.domain.Email;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.auth.details.domain.AuthDetails;
import pl.edu.wit.auth.details.domain.AuthDetailsRole;
import pl.edu.wit.auth.details.domain.AuthDetailsStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.edu.wit.auth.details.domain.AuthDetailsRole.HAIRDRESSER;
import static pl.edu.wit.auth.details.domain.AuthDetailsRole.MEMBER;
import static pl.edu.wit.auth.details.domain.AuthDetailsStatus.ACTIVE;
import static pl.edu.wit.auth.details.domain.AuthDetailsStatus.NOT_ACTIVE;

class AuthDetailsTest {

    private final String id = UUID.randomUUID().toString();
    private final Email email = new Email("test@gmail.com");
    private final String password = "test_password";
    private final String passwordEncoded = "password_encoded";
    private final AuthDetailsRole role = MEMBER;
    private final AuthDetailsStatus status = ACTIVE;

    @Test
    void shouldCreateAuthDetailsByAuthDetailsDto() {
        var authDetailsDto = buildDto();

        var authDetails = new AuthDetails(authDetailsDto);
        assertEquals(authDetailsDto, authDetails.toDto());
    }

    @Test
    void shouldUpdateEmailAuthDetailsByAuthDetailsUpdateCommand() {
        var updateCommand = AuthDetailsUpdateCommand.builder()
                .email("test2@gmail.com")
                .build();
        var authDetailsDto = buildDto();

        var updatedAuthDetails = new AuthDetails(authDetailsDto).update(updateCommand, value -> passwordEncoded);
        var updatedAuthDetailsDto = updatedAuthDetails.toDto();

        assertEquals(authDetailsDto.getId(), updatedAuthDetailsDto.getId());
        assertNotEquals(authDetailsDto.getEmail(), updatedAuthDetailsDto.getEmail());
        assertEquals(updateCommand.getEmail(), updatedAuthDetailsDto.getEmail());
        assertEquals(authDetailsDto.getPassword(), updatedAuthDetailsDto.getPassword());
        assertEquals(authDetailsDto.getRole(), updatedAuthDetailsDto.getRole());
        assertEquals(authDetailsDto.getStatus(), updatedAuthDetailsDto.getStatus());
    }

    @Test
    void shouldUpdatePasswordAuthDetailsByAuthDetailsUpdateCommand() {
        var updateCommand = AuthDetailsUpdateCommand.builder()
                .password("test_password_2")
                .build();
        var authDetailsDto = buildDto();

        var updatedAuthDetails = new AuthDetails(authDetailsDto).update(updateCommand, value -> passwordEncoded);
        var updatedAuthDetailsDto = updatedAuthDetails.toDto();

        assertEquals(authDetailsDto.getId(), updatedAuthDetailsDto.getId());
        assertEquals(authDetailsDto.getEmail(), updatedAuthDetailsDto.getEmail());
        assertNotEquals(authDetailsDto.getPassword(), updatedAuthDetailsDto.getPassword());
        assertNotEquals(updateCommand.getPassword(), updatedAuthDetailsDto.getEmail());
        assertEquals(passwordEncoded, updatedAuthDetailsDto.getPassword());
        assertEquals(authDetailsDto.getRole(), updatedAuthDetailsDto.getRole());
        assertEquals(authDetailsDto.getStatus(), updatedAuthDetailsDto.getStatus());
    }

    @Test
    void shouldUpdateRoleAuthDetailsByAuthDetailsUpdateCommand() {
        var updateCommand = AuthDetailsUpdateCommand.builder()
                .role(HAIRDRESSER)
                .build();
        var authDetailsDto = buildDto();

        var updatedAuthDetails = new AuthDetails(authDetailsDto).update(updateCommand, value -> "password_encoded");
        var updatedAuthDetailsDto = updatedAuthDetails.toDto();

        assertEquals(authDetailsDto.getId(), updatedAuthDetailsDto.getId());
        assertEquals(authDetailsDto.getEmail(), updatedAuthDetailsDto.getEmail());
        assertEquals(authDetailsDto.getPassword(), updatedAuthDetailsDto.getPassword());
        assertNotEquals(authDetailsDto.getRole(), updatedAuthDetailsDto.getRole());
        assertEquals(updateCommand.getRole(), updatedAuthDetailsDto.getRole());
        assertEquals(authDetailsDto.getStatus(), updatedAuthDetailsDto.getStatus());
    }

    @Test
    void shouldUpdateStatusAuthDetailsByAuthDetailsUpdateCommand() {
        var updateCommand = AuthDetailsUpdateCommand.builder()
                .status(NOT_ACTIVE)
                .build();
        var authDetailsDto = buildDto();

        var updatedAuthDetails = new AuthDetails(authDetailsDto).update(updateCommand, value -> "password_encoded");
        var updatedAuthDetailsDto = updatedAuthDetails.toDto();

        assertEquals(authDetailsDto.getId(), updatedAuthDetailsDto.getId());
        assertEquals(authDetailsDto.getEmail(), updatedAuthDetailsDto.getEmail());
        assertEquals(authDetailsDto.getPassword(), updatedAuthDetailsDto.getPassword());
        assertEquals(authDetailsDto.getRole(), updatedAuthDetailsDto.getRole());
        assertNotEquals(authDetailsDto.getStatus(), updatedAuthDetailsDto.getStatus());
        assertEquals(updateCommand.getStatus(), updatedAuthDetailsDto.getStatus());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails(null, email, () -> password, status, role);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails("  ", email, () -> password, status, role);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenEmailIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails(id, null, () -> password, status, role);
        });
        assertEquals("Auth details email cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPasswordIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails(id, email, null, status, role);
        });
        assertEquals("Auth details password cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenStatusIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails(id, email, () -> password, null, role);
        });
        assertEquals("Auth details status cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenRoleIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AuthDetails(id, email, () -> password, status, null);
        });
        assertEquals("Auth details role cannot be null", validationException.getMessage());
    }

    private AuthDetailsDto buildDto() {
        return AuthDetailsDto.builder()
                .id(id)
                .email(email.value())
                .password(password)
                .role(role)
                .status(status)
                .build();
    }

}
