package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public record ServiceCategoryResponse(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull Integer order,
        @NotNull List<ServiceResponse> services
) {

    public ServiceCategoryResponse {
        services = nonNull(services) ? new ArrayList<>(services) : new ArrayList<>();
    }

    public static ServiceCategoryResponse of(ServiceCategoryDto dto) {
        return builder()
                .id(dto.id())
                .name(dto.name())
                .order(dto.order())
                .build();
    }

    public void addServices(Set<ServiceResponse> servicesToAdd) {
        if (servicesToAdd != null && !servicesToAdd.isEmpty()) {
            services().addAll(servicesToAdd.stream()
                    .sorted(Comparator.comparing(ServiceResponse::name))
                    .toList());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private String name;
        private Integer order;
        private List<ServiceResponse> services = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public Builder services(List<ServiceResponse> services) {
            if (services != null) {
                this.services = new ArrayList<>(services);
            }
            return this;
        }

        public ServiceCategoryResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(name, "name must not be null");
            requireNonNull(order, "order must not be null");
            return new ServiceCategoryResponse(id, name, order, services);
        }

    }

}

