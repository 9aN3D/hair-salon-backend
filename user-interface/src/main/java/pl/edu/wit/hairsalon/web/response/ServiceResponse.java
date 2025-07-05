package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public record ServiceResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull MoneyDto price,
        @NotNull Long durationInMinutes,
        String categoryName,
        Integer categoryOrder
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServiceResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static ServiceResponse of(ServiceDto dto) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .durationInMinutes(dto.durationInMinutes())
                .build();
    }

    public static ServiceResponse of(ServiceDto dto, ServiceCategoryDto category) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .price(dto.price())
                .durationInMinutes(dto.durationInMinutes())
                .categoryName(category.name())
                .categoryOrder(category.order())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private String name;
        private MoneyDto price;
        private Long durationInMinutes;
        private String categoryName;
        private Integer categoryOrder;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(MoneyDto price) {
            this.price = price;
            return this;
        }

        public Builder durationInMinutes(Long durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder categoryOrder(Integer categoryOrder) {
            this.categoryOrder = categoryOrder;
            return this;
        }

        public ServiceResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            requireNonNull(price, "price must not be null");
            requireNonNull(durationInMinutes, "durationInMinutes must not be null");

            return new ServiceResponse(id, name, price, durationInMinutes, categoryName, categoryOrder);
        }

    }

}
