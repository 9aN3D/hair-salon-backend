package pl.edu.wit.hairsalon.appointment.command;

import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public record AppointmentCreateCommand(
        String memberId,
        String reservationId,
        DateRangeDto times,
        List<ServiceDto> services,
        String hairdresserId,
        LocalDateTime creationDateTime
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String memberId;
        private String reservationId;
        private DateRangeDto times;
        private List<ServiceDto> services;
        private String hairdresserId;
        private LocalDateTime creationDateTime;

        Builder() {
        }

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder services(List<ServiceDto> services) {
            this.services = services;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }
        
        public AppointmentCreateCommand build() {
            return new AppointmentCreateCommand(
                    memberId,
                    reservationId,
                    times,
                    services,
                    hairdresserId,
                    creationDateTime
            );
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Builder.class.getSimpleName() + "[", "]")
                    .add("memberId='" + memberId + "'")
                    .add("reservationId='" + reservationId + "'")
                    .add("times=" + times)
                    .add("services=" + services)
                    .add("hairdresserId='" + hairdresserId + "'")
                    .add("creationDateTime=" + creationDateTime)
                    .toString();
        }

    }

}
