package pl.edu.wit.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.product.dto.ProductDto;

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
    ProductCategoryConciseResponse category;

    public static ProductResponse of(ProductDto dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .durationInMinutes(dto.getDurationInMinutes())
                .category(ProductCategoryConciseResponse.builder()
                        .id(dto.getCategory().getId())
                        .name(dto.getCategory().getName())
                        .build())
                .build();
    }

}
