package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.dto.AppointmentStatusDto;

enum AppointmentStatus {

    RESERVED,
    RESIGNED,
    CANCELED,
    ABSENCE,
    PRESENCE;

    AppointmentStatusDto toDto() {
        return AppointmentStatusDto.valueOf(this.name());
    }
}
