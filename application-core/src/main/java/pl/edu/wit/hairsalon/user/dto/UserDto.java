package pl.edu.wit.hairsalon.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(String id, UserFullNameDto fullName, UserContactDto contact, LocalDateTime registrationDateTime) {

}
