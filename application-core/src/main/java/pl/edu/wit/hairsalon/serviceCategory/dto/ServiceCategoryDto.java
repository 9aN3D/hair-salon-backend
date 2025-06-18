package pl.edu.wit.hairsalon.serviceCategory.dto;

import java.util.Set;

public record ServiceCategoryDto(
        String id,
        String name,
        Integer order,
        ServiceCategoryStatusDto status,
        Set<String> itemIds
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private Integer order;
        private ServiceCategoryStatusDto status;
        private Set<String> itemIds;

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

        public Builder status(ServiceCategoryStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder itemIds(Set<String> itemIds) {
            this.itemIds = itemIds;
            return this;
        }

        public ServiceCategoryDto build() {
            return new ServiceCategoryDto(id, name, order, status, itemIds);
        }
    }
}

