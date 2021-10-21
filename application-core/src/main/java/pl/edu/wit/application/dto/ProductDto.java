package pl.edu.wit.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.application.domain.model.product.ProductType;

import java.math.BigDecimal;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class ProductDto {

    String id;

    String name;

    ProductType type;

    BigDecimal price;

    Long durationInMinutes;

    ProductCategoryDto category;

}
