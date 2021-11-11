package pl.edu.wit.hairsalon.hairdresser.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class HairdresserDto {

    String id;

    HairdresserFullNameDto fullName;

    String photoId;

    Set<String> serviceIds;

}
