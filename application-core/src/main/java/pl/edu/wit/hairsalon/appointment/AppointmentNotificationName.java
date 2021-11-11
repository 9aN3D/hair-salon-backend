package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentNotificationNameDto;

enum AppointmentNotificationName {

    REMINDER;

    AppointmentNotificationNameDto toDto() {
        return AppointmentNotificationNameDto.valueOf(this.name());
    }
}
