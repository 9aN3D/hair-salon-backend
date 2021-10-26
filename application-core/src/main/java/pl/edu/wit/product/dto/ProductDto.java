package pl.edu.wit.product.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.wit.product.domain.ProductType;

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
