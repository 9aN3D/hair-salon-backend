package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.appointment.command.AppointmentUpdateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;

@RequiredArgsConstructor
class AppointmentUpdater {

    AppointmentDto update(String appointmentId, AppointmentUpdateCommand command) {
        return null;
    }

}
