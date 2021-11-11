package pl.edu.wit.hairsalon.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class AppointmentServicesDto {

    String name;

    Long durationInMinutes;

    BigDecimal price;

    List<AppointmentServiceDto> services;

}
