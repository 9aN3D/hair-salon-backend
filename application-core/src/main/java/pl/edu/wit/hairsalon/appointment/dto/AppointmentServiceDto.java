package pl.edu.wit.hairsalon.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

@Value
@Builder
@AllArgsConstructor
public class AppointmentServiceDto {

    String serviceId;

    String name;

    MoneyDto price;

    Long durationInMinutes;

}
