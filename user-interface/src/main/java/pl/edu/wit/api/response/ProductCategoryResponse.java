package pl.edu.wit.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.application.dto.ProductCategoryDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductCategoryResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    @NotNull
    Set<ProductResponse> products = new HashSet<>();

    public void addProducts(Set<ProductResponse> products) {
        if (nonNull(products) && !products.isEmpty()) {
            this.products.addAll(products);
        }
    }

    public static ProductCategoryResponse of(ProductCategoryDto dto) {
        return ProductCategoryResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
