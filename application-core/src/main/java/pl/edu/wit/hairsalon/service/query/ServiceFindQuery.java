package pl.edu.wit.hairsalon.service.query;

import java.util.Set;

public record ServiceFindQuery(
        String id,
        String name,
        Set<String> ids
) {

    public static ServiceFindQuery empty() {
        return ServiceFindQuery.builder()
                .build();
    }

    public static ServiceFindQuery withId(String serviceId) {
        return ServiceFindQuery.builder()
                .id(serviceId)
                .build();
    }

    public static ServiceFindQuery withName(String name) {
        return ServiceFindQuery.builder()
                .name(name)
                .build();
    }

    public static ServiceFindQuery withIds(Set<String> serviceIds) {
        return ServiceFindQuery.builder()
                .ids(serviceIds)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private Set<String> ids;

        Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ids(Set<String> ids) {
            this.ids = ids;
            return this;
        }

        public ServiceFindQuery build() {
            return new ServiceFindQuery(this.id, this.name, this.ids);
        }

    }

}
