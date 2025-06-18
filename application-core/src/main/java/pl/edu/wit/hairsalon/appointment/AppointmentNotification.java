package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentNotificationDto;
import pl.edu.wit.hairsalon.sharedKernel.SelfValidator;

import java.util.Objects;

record AppointmentNotification(AppointmentNotificationName name, boolean sent) implements SelfValidator<AppointmentNotification> {

    AppointmentNotification(AppointmentNotificationName name) {
        this(name, false);
    }

    AppointmentNotification(AppointmentNotificationDto arg) {
        this(AppointmentNotificationName.valueOf(arg.name().name()), arg.sent());
    }

    AppointmentNotificationDto toDto() {
        return new AppointmentNotificationDto(name.toDto(), sent);
    }

    @Override
    public AppointmentNotification validate() {
        Objects.requireNonNull(name, "Appointment notification name must not be null");
        return this;
    }

    AppointmentNotification changeSent() {
        return new AppointmentNotification(name, true);
    }

}
