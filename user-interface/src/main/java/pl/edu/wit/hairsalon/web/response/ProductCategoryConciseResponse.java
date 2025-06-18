package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

import java.util.Objects;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

public record ProductCategoryConciseResponse(
        @NotBlank String id,
        @NotBlank String name
) {

    public static ProductCategoryConciseResponse of(ServiceCategoryDto serviceCategoryDto) {
        return builder()
                .id(serviceCategoryDto.id())
                .name(serviceCategoryDto.name())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategoryConciseResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public ProductCategoryConciseResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            return new ProductCategoryConciseResponse(id, name);
        }

    }

}
