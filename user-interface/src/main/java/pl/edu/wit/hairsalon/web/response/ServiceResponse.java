package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    String categoryName;

    public static ServiceResponse of(ServiceDto dto) {
        return getServiceResponseBuilder(dto)
                .build();
    }

    public static ServiceResponse of(ServiceDto dto, String categoryName) {
        return getServiceResponseBuilder(dto)
                .categoryName(categoryName)
                .build();
    }

    private static ServiceResponseBuilder getServiceResponseBuilder(ServiceDto dto) {
        return ServiceResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .durationInMinutes(dto.getDurationInMinutes());
    }

}
