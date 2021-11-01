package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedkernel.dto.MoneyDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ServiceResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotNull
    MoneyDto price;

    @NotNull
    Long durationInMinutes;

    public static ServiceResponse of(ServiceDto dto) {
        return ServiceResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .durationInMinutes(dto.getDurationInMinutes())
                .build();
    }

}
