package pl.edu.wit.hairsalon.token.dto;

import lombok.Builder;

@Builder
public record AccessTokenDto(String value, String refreshToken) {

}
