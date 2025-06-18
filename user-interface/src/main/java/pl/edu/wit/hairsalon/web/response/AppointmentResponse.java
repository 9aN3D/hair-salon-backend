package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServicesDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.Objects;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

public record AppointmentResponse(
        @NotBlank String id,
        @NotNull DateRangeDto times,
        @NotNull AppointmentServicesDto appointmentServices,
        @NotNull AppointmentStatusDto status,
        @NotNull HairdresserResponse hairdresser
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private DateRangeDto times;
        private AppointmentServicesDto appointmentServices;
        private AppointmentStatusDto status;
        private HairdresserResponse hairdresser;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder appointmentServices(AppointmentServicesDto appointmentServices) {
            this.appointmentServices = appointmentServices;
            return this;
        }

        public Builder status(AppointmentStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder hairdresser(HairdresserResponse hairdresser) {
            this.hairdresser = hairdresser;
            return this;
        }

        public AppointmentResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(times, "times must not be null");
            requireNonNull(appointmentServices, "appointmentServices must not be null");
            requireNonNull(status, "status must not be null");
            requireNonNull(hairdresser, "hairdresser must not be null");
            return new AppointmentResponse(id, times, appointmentServices, status, hairdresser);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

}

