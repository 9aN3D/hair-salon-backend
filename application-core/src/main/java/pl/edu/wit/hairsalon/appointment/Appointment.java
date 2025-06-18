package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.exception.AppointmentResignException;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESERVED;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESIGNED;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.valueOf;

record Appointment(
        String id,
        String reservationId,
        String memberId,
        DateRange times,
        AppointmentServices services,
        AppointmentStatus status,
        LocalDateTime creationDateTime,
        LocalDateTime statusModificationDateTime,
        String hairdresserId,
        AppointmentNotification notification
) implements SelfValidator<Appointment> {

    Appointment(AppointmentDto arg) {
        this(
                arg.id(),
                arg.reservationId(),
                arg.memberId(),
                new DateRange(arg.times()),
                new AppointmentServices(arg.services()),
                valueOf(arg.status().name()),
                arg.creationDateTime(),
                arg.statusModificationDateTime(),
                arg.hairdresserId(),
                new AppointmentNotification(arg.notification())
        );
    }

    AppointmentDto toDto() {
        return AppointmentDto.builder()
                .id(id)
                .reservationId(reservationId)
                .memberId(memberId)
                .times(times.toDto())
                .services(services.toDto())
                .status(status.toDto())
                .creationDateTime(creationDateTime)
                .statusModificationDateTime(statusModificationDateTime)
                .hairdresserId(hairdresserId)
                .notification(notification.toDto())
                .build();
    }

    @Override
    public Appointment validate() {
        requireNonNull(times, "Appointment data range times must not be null");
        requireNonNull(services, "Appointment services must not be null");
        requireNonNull(status, "Appointment status must not be null");
        requireNonNull(creationDateTime, "Appointment creation date time must not be null");
        requireNonNull(statusModificationDateTime, "Appointment status modification date time must not be null");
        requireNonNull(notification, "Appointment notification must not be null");
        validate(new NotBlankString(id), new NotBlankString(reservationId), new NotBlankString(memberId), new NotBlankString(hairdresserId), times, services, notification);
        return this;
    }

    Appointment resign(Consumer<String> action) {
        if (times.start().isBefore(LocalDateTime.now())) {
            throw new AppointmentResignException(
                    format("Time has elapsed for resignation, appointment times %s", times)
            );
        }
        if (status != RESERVED) {
            throw new AppointmentResignException(
                    format("Resignation is not possible due to the established status %s", status)
            );
        }
        action.accept(reservationId);
        return Appointment.builder()
                .id(id)
                .reservationId(reservationId)
                .memberId(memberId)
                .times(times)
                .services(services)
                .status(RESIGNED)
                .creationDateTime(creationDateTime)
                .statusModificationDateTime(LocalDateTime.now())
                .hairdresserId(hairdresserId)
                .notification(notification)
                .build();
    }

    Appointment changeNotificationSent() {
        return Appointment.builder()
                .id(id)
                .reservationId(reservationId)
                .memberId(memberId)
                .times(times)
                .services(services)
                .status(status)
                .creationDateTime(creationDateTime)
                .statusModificationDateTime(LocalDateTime.now())
                .hairdresserId(hairdresserId)
                .notification(notification.changeSent())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String reservationId;
        private String memberId;
        private DateRange times;
        private AppointmentServices services;
        private AppointmentStatus status;
        private LocalDateTime creationDateTime;
        private LocalDateTime statusModificationDateTime;
        private String hairdresserId;
        private AppointmentNotification notification;

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

        public Builder times(DateRange times) {
            this.times = times;
            return this;
        }

        public Builder services(AppointmentServices services) {
            this.services = services;
            return this;
        }

        public Builder status(AppointmentStatus status) {
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

        public Builder notification(AppointmentNotification notification) {
            this.notification = notification;
            return this;
        }

        public Appointment build() {
            return new Appointment(
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
