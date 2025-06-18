package pl.edu.wit.hairsalon.service.query;

import java.util.Set;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static pl.edu.wit.hairsalon.sharedKernel.CollectionHelper.nonNullOrEmpty;

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

    public void ifIdPresent(Consumer<String> action) {
        if (nonNull(id)) {
            action.accept(id);
        }
    }

    public void ifNamePresent(Consumer<String> action) {
        if (nonNull(name) && !name.isBlank()) {
            action.accept(name);
        }
    }

    public void ifIdsPresent(Consumer<Set<String>> action) {
        if (nonNullOrEmpty(ids)) {
            action.accept(ids);
        }
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
