package pl.edu.wit.hairsalon.appointment;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentNotificationDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;

import java.util.Objects;

@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class AppointmentNotification implements SelfValidator<AppointmentNotification> {

    private final AppointmentNotificationName name;
    private final boolean sent;

    AppointmentNotification(AppointmentNotificationName name) {
        this(name, false);
    }

    AppointmentNotification(AppointmentNotificationDto arg) {
        this(AppointmentNotificationName.valueOf(arg.getName().name()), arg.isSent());
    }

    AppointmentNotificationDto toDto() {
        return AppointmentNotificationDto.builder()
                .name(name.toDto())
                .sent(sent)
                .build();
    }

    @Override
    public AppointmentNotification validate() {
        Objects.requireNonNull(name, "Appointment notification name must not be null");
        return this;
    }

    AppointmentNotification changeSent() {
        return AppointmentNotification.builder()
                .name(name)
                .sent(true)
                .build();
    }

}
