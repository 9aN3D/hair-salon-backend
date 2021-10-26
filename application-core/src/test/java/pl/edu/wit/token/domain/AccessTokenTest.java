package pl.edu.wit.token.domain;

import org.junit.jupiter.api.Test;
import pl.edu.wit.token.dto.AccessTokenDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.token.domain.AccessToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccessTokenTest {

    private final AccessTokenDto dto = buildAccessToken();
    private final static String BLANK_VALUE = "  ";

    @Test
    void shouldCreateAccessTokenByDto() {
        var accessToken = new AccessToken(dto);

        assertEquals(dto, accessToken.toDto());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(null, null, null, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenValueIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(BLANK_VALUE, null, null, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenTokenTypeIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(dto.getValue(), null, null, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenTokenTypeIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(dto.getValue(), BLANK_VALUE, null, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenRefreshTokenIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(dto.getValue(), dto.getTokenType(), null, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenRefreshTokenIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(dto.getValue(), dto.getTokenType(), BLANK_VALUE, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenExpiresInIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new AccessToken(dto.getValue(), dto.getTokenType(), dto.getRefreshToken(), null);
        });
        assertEquals("Access token expires in value cannot be null", validationException.getMessage());
    }

    private AccessTokenDto buildAccessToken() {
        return AccessTokenDto.builder()
                .value("test_value")
                .refreshToken("refreshToken")
                .tokenType("test")
                .expiresIn(123)
                .build();
    }

}
