package pl.edu.wit.application.domain.model.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.command.product.ProductCategoryUpdateCommand;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.dto.ProductCategoryDto;

import static java.util.Optional.ofNullable;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class ProductCategory {

    private final String id;
    private final String name;

    public ProductCategory(String id, String name) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
    }

    public ProductCategory(ProductCategoryDto dto) {
        this(
                dto.getId(),
                dto.getName()
        );
    }

    public ProductCategory update(ProductCategoryUpdateCommand command) {
        return ProductCategory.builder()
                .id(id)
                .name(ofNullable(command.getName()).orElse(name))
                .build();
    }

    public ProductCategoryDto toDto() {
        return ProductCategoryDto.builder()
                .id(id)
                .name(name)
                .build();
    }

}
