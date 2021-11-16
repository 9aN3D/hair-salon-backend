package pl.edu.wit.hairsalon.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ServiceDto {

    String id;

    String name;

    MoneyDto price;

    Long durationInMinutes;



}
