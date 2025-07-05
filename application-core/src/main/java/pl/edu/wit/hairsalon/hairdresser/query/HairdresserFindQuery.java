package pl.edu.wit.hairsalon.hairdresser.query;

import java.util.Set;

public record HairdresserFindQuery(
        String id,
        String fullName,
        Set<String> serviceIds
) {

    public static HairdresserFindQuery ofHairdresserId(String id) {
        return HairdresserFindQuery.builder()
                .id(id)
                .build();
    }

    public static HairdresserFindQuery ofHairdresserServiceIds(Set<String> serviceIds) {
        return HairdresserFindQuery.builder()
                .serviceIds(serviceIds)
                .build();
    }

    public static HairdresserFindQuery empty() {
        return HairdresserFindQuery.builder()
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String fullName;
        private Set<String> serviceIds;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds;
            return this;
        }

        public HairdresserFindQuery build() {
            return new HairdresserFindQuery(
                    id, fullName, serviceIds
            );
        }

    }

}
