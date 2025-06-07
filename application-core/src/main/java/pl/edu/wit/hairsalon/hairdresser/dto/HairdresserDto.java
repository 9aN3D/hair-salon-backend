package pl.edu.wit.hairsalon.hairdresser.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record HairdresserDto(String id, HairdresserFullNameDto fullName, String photoId, Set<String> serviceIds) {

}
