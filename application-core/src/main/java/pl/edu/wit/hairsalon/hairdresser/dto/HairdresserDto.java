package pl.edu.wit.hairsalon.hairdresser.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.util.Set;

public record HairdresserDto(
        String id,
        FullNameDto fullName,
        String photoId,
        Set<String> serviceIds
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private String photoId;
        private Set<String> serviceIds;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(FullNameDto fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder photoId(String photoId) {
            this.photoId = photoId;
            return this;
        }

        public Builder serviceIds(Set<String> serviceIds) {
            this.serviceIds = serviceIds;
            return this;
        }

        public HairdresserDto build() {
            return new HairdresserDto(id, fullName, photoId, serviceIds);
        }

    }

}
