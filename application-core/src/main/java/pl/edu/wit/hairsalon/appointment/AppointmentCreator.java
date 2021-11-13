package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.appointment.command.AppointmentCreateCommand;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.sharedkernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

import java.time.LocalDateTime;

import static pl.edu.wit.hairsalon.appointment.AppointmentNotificationName.REMINDER;
import static pl.edu.wit.hairsalon.appointment.AppointmentStatus.RESERVED;

@RequiredArgsConstructor
class AppointmentCreator {

    private final AppointmentPort appointmentPort;
    private final IdGenerator idGenerator;
    //private final NotificationFacade notificationFacade; TODO create AppointmentNotification

    AppointmentDto create(AppointmentCreateCommand command) {
        var appointment = createNewAppointment(command)
                .validate();
        return appointmentPort.save(appointment.toDto());
    }

    private Appointment createNewAppointment(AppointmentCreateCommand command) {
        return Appointment.builder()
                .id(idGenerator.generate())
                .reservationId(command.getReservationId())
                .memberId(command.getMemberId())
                .times(new DateRange(command.getTimes()))
                .services(new AppointmentServices().addAll(command.getServices()))
                .status(RESERVED)
                .creationDateTime(command.getCreationDateTime())
                .statusModificationDateTime(LocalDateTime.now())
                .hairdresserId(command.getHairdresserId())
                .notification(new AppointmentNotification(REMINDER))
                .build();
    }

}
