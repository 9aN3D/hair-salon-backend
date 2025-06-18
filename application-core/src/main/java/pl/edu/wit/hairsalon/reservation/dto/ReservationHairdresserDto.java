package pl.edu.wit.hairsalon.reservation.dto;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;

import java.util.List;

public record ReservationHairdresserDto(
        String id,
        FullNameDto fullName,
        String photoId,
        List<ServiceDto> services
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private FullNameDto fullName;
        private String photoId;
        private List<ServiceDto> services;

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

        public Builder services(List<ServiceDto> services) {
            this.services = services;
            return this;
        }

        public ReservationHairdresserDto build() {
            return new ReservationHairdresserDto(id, fullName, photoId, services);
        }
    }
    
}
