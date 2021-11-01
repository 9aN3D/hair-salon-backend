package pl.edu.wit.hairsalon.hairdresser.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class HairdresserDto {

    String id;

    HairdresserFullNameDto fullName;

    String photoId;

    Set<String> services;

}
