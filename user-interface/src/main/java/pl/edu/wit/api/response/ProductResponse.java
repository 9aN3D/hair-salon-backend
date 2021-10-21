package pl.edu.wit.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.application.dto.ProductDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotNull
    BigDecimal price;

    @NotNull
    Long durationInMinutes;

    @NotBlank
    String categoryId;

    public static ProductResponse of(ProductDto dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .durationInMinutes(dto.getDurationInMinutes())
                .categoryId(dto.getCategory().getId())
                .build();
    }

}
