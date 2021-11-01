package pl.edu.wit.hairsalon.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {

    private String value;

    private String tokenType;

    private String refreshToken;

    private Integer expiresIn;

}
