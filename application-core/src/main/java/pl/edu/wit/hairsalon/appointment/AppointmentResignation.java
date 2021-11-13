package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.scheduledevent.ScheduledEventFacade;

@RequiredArgsConstructor
class AppointmentResignation {

    private final AppointmentPort appointmentPort;
    private final ScheduledEventFacade scheduledEventFacade;

    AppointmentDto resign(String memberId, String appointmentId) {
        var appointment = appointmentPort.findOneOrThrow(AppointmentFindQuery.with(memberId, appointmentId));
        var resignedAppointment = new Appointment(appointment)
                .validate()
                .resign(scheduledEventFacade::delete)
                .toDto();
        return appointmentPort.save(resignedAppointment);
    }

}
