package pl.edu.wit.hairsalon.appointment.dto;

import java.math.BigDecimal;
import java.util.List;

public record AppointmentServicesDto(
        String name,
        Long durationInMinutes,
        BigDecimal price,
        List<AppointmentServiceDto> services
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private Long durationInMinutes;
        private BigDecimal price;
        private List<AppointmentServiceDto> services;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder durationInMinutes(Long durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder services(List<AppointmentServiceDto> services) {
            this.services = services;
            return this;
        }

        public AppointmentServicesDto build() {
            return new AppointmentServicesDto(
                    name,
                    durationInMinutes,
                    price,
                    services
            );
        }
    }
    
}
