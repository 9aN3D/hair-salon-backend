package pl.edu.wit.hairsalon.serviceCategory.query;

import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryStatusDto;

import java.util.Set;

public record ServiceCategoryFindQuery(
        String name,
        String serviceCategoryId,
        ServiceCategoryStatusDto status,
        Set<String> serviceIds,
        Integer order
) {

    public static ServiceCategoryFindQuery empty() {
        return ServiceCategoryFindQuery.builder()
                .build();
    }

    public static ServiceCategoryFindQuery withName(String name) {
        return ServiceCategoryFindQuery.builder()
                .name(name)
                .build();
    }

    public static ServiceCategoryFindQuery withCategoryId(String serviceCategoryId) {
        return ServiceCategoryFindQuery.builder()
                .serviceCategoryId(serviceCategoryId)
                .build();
    }

    public static ServiceCategoryFindQuery withServiceId(String serviceId) {
        return ServiceCategoryFindQuery.builder()
                .serviceIds(Set.of(serviceId))
                .build();
    }

    public static ServiceCategoryFindQuery withServiceIds(Set<String> serviceIds) {
        return ServiceCategoryFindQuery.builder()
                .serviceIds(serviceIds)
                .build();
    }

    public static ServiceCategoryFindQuery withOrder(Integer order) {
        return ServiceCategoryFindQuery.builder()
                .order(order)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String serviceCategoryId;
        private ServiceCategoryStatusDto status;
        private Set<String> serviceIds;
        private Integer order;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder serviceCategoryId(String serviceCategoryId) {
            this.serviceCategoryId = serviceCategoryId;
            return this;
        }

        public Builder status(ServiceCategoryStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public ServiceCategoryFindQuery build() {
            return new ServiceCategoryFindQuery(name, serviceCategoryId, status, serviceIds, order);
        }

    }

}
