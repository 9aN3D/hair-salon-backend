package pl.edu.wit.application.domain.model.product;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.domain.model.Money;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.application.dto.ProductDto;
import pl.edu.wit.application.exception.ValidationException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.edu.wit.application.domain.model.product.ProductType.SERVICE;

class ProductTest {

    private final String id = UUID.randomUUID().toString();
    private final String name = "Strzyżenie męskie";
    private final ProductType type = SERVICE;
    private final BigDecimal price = new BigDecimal("50.50");
    private final Long durationInMinutes = 45L;
    private final ProductCategoryDto categoryDto = buildProductCategoryDto();

    @Test
    void shouldProductByProductDto() {
        var productDto = buildDto();

        var product = new Product(productDto);
        assertEquals(productDto, product.toDto());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(null, name, type, new Money(price), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(" ", name, type, new Money(price), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, null, type, new Money(price), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, "  ", type, new Money(price), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenTypeIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, null, new Money(price), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("Product type cannot be null", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPriceIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, type, null, Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("Product price cannot be null or negative", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenPriceIsLessThanZero() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, type, new Money(new BigDecimal("-1")), Duration.ZERO, new ProductCategory(categoryDto));
        });
        assertEquals("Product price cannot be null or negative", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenDurationIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, type, new Money(price), null, new ProductCategory(categoryDto));
        });
        assertEquals("Product duration cannot be null or negative", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenDurationIsLessThanZero() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, type, new Money(price), Duration.ofMinutes(-1), new ProductCategory(categoryDto));
        });
        assertEquals("Product duration cannot be null or negative", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenCategoryIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new Product(id, name, type, new Money(price), Duration.ofMinutes(durationInMinutes), null);
        });
        assertEquals("Product category cannot be null", validationException.getMessage());
    }

    @Test
    void shouldUpdateProductNameByProductUpdateCommand() {
        var updateCommand = ProductUpdateCommand.builder()
                .name("Updated name")
                .build();
        var productDto = buildDto();

        var updatedProduct = new Product(productDto).update(updateCommand);
        var updatedProductDto = updatedProduct.toDto();

        assertEquals(productDto.getId(), updatedProductDto.getId());
        assertNotEquals(productDto.getName(), updatedProductDto.getName());
        assertEquals(updatedProductDto.getName(), updateCommand.getName());
        assertEquals(productDto.getType(), updatedProductDto.getType());
        assertEquals(productDto.getPrice(), updatedProductDto.getPrice());
        assertEquals(productDto.getDurationInMinutes(), updatedProductDto.getDurationInMinutes());
        assertEquals(productDto.getCategory(), updatedProductDto.getCategory());
    }

    @Test
    void shouldUpdateProductPriceByProductUpdateCommand() {
        var updateCommand = ProductUpdateCommand.builder()
                .price(new BigDecimal("70.10"))
                .build();
        var productDto = buildDto();

        var updatedProduct = new Product(productDto).update(updateCommand);
        var updatedProductDto = updatedProduct.toDto();

        assertEquals(productDto.getId(), updatedProductDto.getId());
        assertEquals(productDto.getName(), updatedProductDto.getName());
        assertEquals(productDto.getType(), updatedProductDto.getType());
        assertNotEquals(productDto.getPrice(), updatedProductDto.getPrice());
        assertEquals(updatedProductDto.getPrice(), updateCommand.getPrice());
        assertEquals(productDto.getDurationInMinutes(), updatedProductDto.getDurationInMinutes());
        assertEquals(productDto.getCategory(), updatedProductDto.getCategory());
    }

    @Test
    void shouldUpdateProductDurationInMinutesByProductUpdateCommand() {
        var updateCommand = ProductUpdateCommand.builder()
                .durationInMinutes(50L)
                .build();
        var productDto = buildDto();

        var updatedProduct = new Product(productDto).update(updateCommand);
        var updatedProductDto = updatedProduct.toDto();

        assertEquals(productDto.getId(), updatedProductDto.getId());
        assertEquals(productDto.getName(), updatedProductDto.getName());
        assertEquals(productDto.getType(), updatedProductDto.getType());
        assertEquals(productDto.getPrice(), updatedProductDto.getPrice());
        assertNotEquals(productDto.getDurationInMinutes(), updatedProductDto.getDurationInMinutes());
        assertEquals(updatedProductDto.getDurationInMinutes(), updateCommand.getDurationInMinutes());
        assertEquals(productDto.getCategory(), updatedProductDto.getCategory());
    }

    private ProductDto buildDto() {
        return ProductDto.builder()
                .id(id)
                .name(name)
                .type(type)
                .price(price)
                .durationInMinutes(durationInMinutes)
                .category(categoryDto)
                .build();
    }

    private ProductCategoryDto buildProductCategoryDto() {
        return ProductCategoryDto.builder()
                .id(UUID.randomUUID().toString())
                .name("Strzyżenie")
                .build();
    }

}
