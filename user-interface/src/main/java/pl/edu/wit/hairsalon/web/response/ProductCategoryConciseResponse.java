package pl.edu.wit.hairsalon.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductCategoryConciseResponse {

    @NotBlank
    String id;

    @NotBlank
    String name;

    static ProductCategoryConciseResponse of(ServiceCategoryDto serviceCategoryDto) {
        return ProductCategoryConciseResponse.builder()
                .id(serviceCategoryDto.getId())
                .name(serviceCategoryDto.getName())
                .build();
    }

}
