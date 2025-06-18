package pl.edu.wit.hairsalon.appointment;

import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.port.secondary.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static pl.edu.wit.hairsalon.appointment.AppointmentNotificationName.REMINDER;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESERVED;

class AppointmentCreator {

    private final AppointmentPort appointmentPort;
    private final IdGenerator idGenerator;

    AppointmentCreator(AppointmentPort appointmentPort, IdGenerator idGenerator) {
        this.appointmentPort = appointmentPort;
        this.idGenerator = idGenerator;
    }

    AppointmentDto create(AppointmentCreateCommand command) {
        var appointment = createNewAppointment(command)
                .validate();
        return appointmentPort.save(appointment.toDto());
    }

    private Appointment createNewAppointment(AppointmentCreateCommand command) {
        return Appointment.builder()
                .id(idGenerator.generate())
                .reservationId(command.reservationId())
                .memberId(command.memberId())
                .times(new DateRange(command.times()))
                .services(new AppointmentServices().addAll(command.services()))
                .status(RESERVED)
                .creationDateTime(command.creationDateTime())
                .statusModificationDateTime(LocalDateTime.now())
                .hairdresserId(command.hairdresserId())
                .notification(new AppointmentNotification(REMINDER))
                .build();
    }

}
