package pl.edu.wit.application.domain.model.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.domain.model.Money;
import pl.edu.wit.application.domain.model.NotBlankString;
import pl.edu.wit.application.dto.ProductDto;
import pl.edu.wit.application.exception.ValidationException;

import java.time.Duration;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Product {

    private final String id;
    private final String name;
    private final ProductType type;
    private final Money price;
    private final Duration duration;
    private final ProductCategory category;

    public Product(String id,
                   String name,
                   ProductType type,
                   Money price,
                   Duration duration,
                   ProductCategory category) {
        this.id = new NotBlankString(id).value();
        this.name = new NotBlankString(name).value();
        this.type = ofNullable(type)
                .orElseThrow(() -> new ValidationException("Product type cannot be null"));
        this.price = ofNullable(price)
                .filter(Money::hasPositiveAmount)
                .orElseThrow(() -> new ValidationException("Product price cannot be null or negative"));
        this.duration = ofNullable(duration)
                .filter(not(Duration::isNegative))
                .orElseThrow(() -> new ValidationException("Product duration cannot be null or negative"));
        this.category = ofNullable(category)
                .orElseThrow(() -> new ValidationException("Product category cannot be null"));
    }

    public Product(ProductDto dto) {
        this(
                dto.getId(),
                dto.getName(),
                dto.getType(),
                new Money(dto.getPrice()),
                Duration.ofMinutes(dto.getDurationInMinutes()),
                new ProductCategory(dto.getCategory())
        );
    }

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .type(type)
                .price(price.amount())
                .durationInMinutes(duration.toMinutes())
                .category(category.toDto())
                .build();
    }

    public Product update(ProductUpdateCommand command) {
        return Product.builder()
                .id(id)
                .type(type)
                .name(ofNullable(command.getName()).orElse(name))
                .price(ofNullable(command.getPrice())
                        .map(Money::new)
                        .orElse(price))
                .duration(ofNullable(command.getDurationInMinutes())
                        .map(Duration::ofMinutes)
                        .orElse(duration))
                .category(category)
                .build();
    }

}
