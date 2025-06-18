package pl.edu.wit.hairsalon.appointment.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

public record AppointmentServiceDto(
        String serviceId,
        String name,
        MoneyDto price,
        Long durationInMinutes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String serviceId;
        private String name;
        private MoneyDto price;
        private Long durationInMinutes;

        public Builder serviceId(String serviceId) {
            this.serviceId = serviceId;
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

        public AppointmentServiceDto build() {
            return new AppointmentServiceDto(
                    serviceId,
                    name,
                    price,
                    durationInMinutes
            );
        }
    }
}
