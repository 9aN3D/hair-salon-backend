package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentServiceDto;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

public record AppointmentConciseResponse(
        @NotBlank String id,
        @NotNull DateRangeDto times,
        @NotNull List<String> appointmentServiceNames,
        @NotNull AppointmentStatusDto status
) {

    public static AppointmentConciseResponse of(AppointmentDto appointment) {
        return builder()
                .id(appointment.id())
                .times(appointment.times())
                .appointmentServiceNames(appointment.services()
                        .services()
                        .stream()
                        .map(AppointmentServiceDto::name)
                        .collect(Collectors.toList()))
                .status(appointment.status())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;
        private DateRangeDto times;
        private List<String> appointmentServiceNames;
        private AppointmentStatusDto status;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder appointmentServiceNames(List<String> appointmentServiceNames) {
            this.appointmentServiceNames = appointmentServiceNames;
            return this;
        }

        public Builder status(AppointmentStatusDto status) {
            this.status = status;
            return this;
        }

        public AppointmentConciseResponse build() {
            requireNonNull(id, "id must not be null");
            requireNonNull(times, "times must not be null");
            requireNonNull(appointmentServiceNames, "appointmentServiceNames must not be null");
            requireNonNull(status, "status must not be null");
            return new AppointmentConciseResponse(id, times, appointmentServiceNames, status);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentConciseResponse that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

}

