package pl.edu.wit.application.domain.model.product;

import org.junit.jupiter.api.Test;
import pl.edu.wit.application.command.product.ProductCategoryUpdateCommand;
import pl.edu.wit.application.command.product.ProductUpdateCommand;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.application.exception.ValidationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductCategoryTest {

    private final String id = UUID.randomUUID().toString();
    private final String name = "Strzyżenie";

    @Test
    void shouldProductCategoryByProductCategoryDto() {
        var productCategoryDto = buildDto();

        var productCategory = new ProductCategory(productCategoryDto);
        assertEquals(productCategoryDto, productCategory.toDto());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new ProductCategory(null, name);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenIdIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new ProductCategory("  ", name);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsNull() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new ProductCategory(id, null);
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsBlank() {
        var validationException = assertThrows(ValidationException.class, () -> {
            new ProductCategory(id, "  ");
        });
        assertEquals("String cannot be null or blank", validationException.getMessage());
    }

    @Test
    void shouldUpdateProductCategoryNameByProductCategoryUpdateCommand() {
        var updateCommand = new ProductCategoryUpdateCommand("Updated name");
        var productCategoryDto = buildDto();

        var updatedProductCategory = new ProductCategory(productCategoryDto).update(updateCommand);
        var updatedProductCategoryDto = updatedProductCategory.toDto();

        assertEquals(productCategoryDto.getId(), updatedProductCategoryDto.getId());
        assertNotEquals(productCategoryDto.getName(), updatedProductCategoryDto.getName());
        assertEquals(updateCommand.getName(), updatedProductCategoryDto.getName());
    }

    private ProductCategoryDto buildDto() {
        return ProductCategoryDto.builder()
                .id(id)
                .name(name)
                .build();
    }

}
