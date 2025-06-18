package pl.edu.wit.hairsalon.appointment.dto;

import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.time.LocalDateTime;

public record AppointmentDto(
        String id,
        String reservationId,
        String memberId,
        DateRangeDto times,
        AppointmentServicesDto services,
        AppointmentStatusDto status,
        LocalDateTime creationDateTime,
        LocalDateTime statusModificationDateTime,
        String hairdresserId,
        AppointmentNotificationDto notification
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String reservationId;
        private String memberId;
        private DateRangeDto times;
        private AppointmentServicesDto services;
        private AppointmentStatusDto status;
        private LocalDateTime creationDateTime;
        private LocalDateTime statusModificationDateTime;
        private String hairdresserId;
        private AppointmentNotificationDto notification;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder reservationId(String reservationId) {
            this.reservationId = reservationId;
            return this;
        }

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder times(DateRangeDto times) {
            this.times = times;
            return this;
        }

        public Builder services(AppointmentServicesDto services) {
            this.services = services;
            return this;
        }

        public Builder status(AppointmentStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder creationDateTime(LocalDateTime creationDateTime) {
            this.creationDateTime = creationDateTime;
            return this;
        }

        public Builder statusModificationDateTime(LocalDateTime statusModificationDateTime) {
            this.statusModificationDateTime = statusModificationDateTime;
            return this;
        }

        public Builder hairdresserId(String hairdresserId) {
            this.hairdresserId = hairdresserId;
            return this;
        }

        public Builder notification(AppointmentNotificationDto notification) {
            this.notification = notification;
            return this;
        }

        public AppointmentDto build() {
            return new AppointmentDto(
                    id,
                    reservationId,
                    memberId,
                    times,
                    services,
                    status,
                    creationDateTime,
                    statusModificationDateTime,
                    hairdresserId,
                    notification
            );
        }

    }

}
