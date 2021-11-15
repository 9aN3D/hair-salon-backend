package pl.edu.wit.hairsalon.appointment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.exception.AppointmentResignException;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESERVED;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESIGNED;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.valueOf;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
class Appointment implements SelfValidator<Appointment> {

    private final String id;
    private final String reservationId;
    private final String memberId;
    private final DateRange times;
    private final AppointmentServices services;
    private final AppointmentStatus status;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime statusModificationDateTime;
    private final String hairdresserId;
    private final AppointmentNotification notification;

    Appointment(AppointmentDto arg) {
        this(
                arg.getId(),
                arg.getReservationId(),
                arg.getMemberId(),
                new DateRange(arg.getTimes()),
                new AppointmentServices(arg.getServices()),
                valueOf(arg.getStatus().name()),
                arg.getCreationDateTime(),
                arg.getStatusModificationDateTime(),
                arg.getHairdresserId(),
                new AppointmentNotification(arg.getNotification())
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

}
