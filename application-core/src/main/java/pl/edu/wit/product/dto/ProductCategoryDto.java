package pl.edu.wit.product.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class ProductCategoryDto {

    String id;

    String name;

}
