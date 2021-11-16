package pl.edu.wit.hairsalon.reservation.dto;

import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.util.List;

@Value
@Builder
public class ReservationHairdresserDto {

    String id;

    FullNameDto fullName;

    String photoId;

    List<ServiceDto> services;

}
