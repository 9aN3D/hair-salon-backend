package pl.edu.wit.hairsalon.appointment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.sharedkernel.SelfValidator;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;
import pl.edu.wit.hairsalon.sharedkernel.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

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
    private final LocalDateTime statusModificationDateTime;
    private final String hairdresserId;
    private final AppointmentNotification notification;

    Appointment verifyReservedTimes(Function<AppointmentFindQuery, Long> countFunction) {
        requireNonNull(countFunction, "Count function must not be null");
        var findQuery = AppointmentFindQuery.withReserved(times.toDto(), hairdresserId);
        if (countFunction.apply(findQuery) != 0) {
            throw new ValidationException(
                    format("These appointment times have already reserved, {hairdresserId=%s, times=%s}", hairdresserId, times)
            );
        }
        return this;
    }

    AppointmentDto toDto() {
        return AppointmentDto.builder()
                .id(id)
                .reservationId(reservationId)
                .memberId(memberId)
                .times(times.toDto())
                .services(services.toDto())
                .status(status.toDto())
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
        requireNonNull(statusModificationDateTime, "Appointment status modification date time must not be null");
        requireNonNull(notification, "Appointment notification must not be null");
        validate(new NotBlankString(id), new NotBlankString(reservationId), new NotBlankString(memberId), new NotBlankString(hairdresserId), times, services, notification);
        return this;
    }

}
