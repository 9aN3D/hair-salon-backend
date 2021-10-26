package pl.edu.wit.token.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.token.dto.AccessTokenDto;
import pl.edu.wit.common.exception.ValidationException;
import pl.edu.wit.common.domain.NotBlankString;

import static java.util.Optional.ofNullable;

@ToString
@EqualsAndHashCode
public class AccessToken {

    private final String value;
    private final String tokenType;
    private final String refreshToken;
    private final Integer expiresIn;

    public AccessToken(String value, String tokenType, String refreshToken, Integer expiresIn) {
        this.value = new NotBlankString(value).value();
        this.tokenType = new NotBlankString(tokenType).value();
        this.refreshToken = new NotBlankString(refreshToken).value();
        this.expiresIn = ofNullable(expiresIn)
                .orElseThrow(() -> new ValidationException("Access token expires in value cannot be null"));
    }

    public AccessToken(AccessTokenDto dto) {
        this(
                dto.getValue(),
                dto.getTokenType(),
                dto.getRefreshToken(),
                dto.getExpiresIn()
        );
    }

    public AccessTokenDto toDto() {
        return AccessTokenDto.builder()
                .value(value)
                .tokenType(tokenType)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }

}
